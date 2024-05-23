package multivaluedictionaryapp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultiStringsDictionaryTest {

  private MultiStringsDictionary multiStringsDictionary;

  @BeforeEach
  public void setUp() {
    multiStringsDictionary = new MultiStringsDictionary();
  }

  @Test
  void testAddNewKeyAndValue() {
    try {
      multiStringsDictionary.add("key1", "value1");
    } catch (MemberAlreadyExistsException e) {
      assertEquals("Error, member already exists for key.", e.getMessage());
    }
    assertTrue(multiStringsDictionary.getMap().containsKey("key1"));
    assertTrue(multiStringsDictionary.getMap().get("key1").contains("value1"));
  }

  @Test
  void testAddExistingKeyAndValue() {
    try {
      multiStringsDictionary.add("key2", "value2");
      multiStringsDictionary.add("key2", "value2");
    } catch (MemberAlreadyExistsException e) {
      assertEquals("Error, member already exists for key.", e.getMessage());
      assertTrue(multiStringsDictionary.getMap().containsKey("key2"));
      assertTrue(multiStringsDictionary.getMap().get("key2").contains("value2"));
      assertTrue(multiStringsDictionary.getMap().size() == 1);
    }
  }

  @Test
  void testGetKeys() throws MemberAlreadyExistsException {
    assertTrue(multiStringsDictionary.getKeys().isEmpty());
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key2", "value1");
    assertTrue(multiStringsDictionary.getKeys().size() == 2);
    assertTrue(multiStringsDictionary.getKeys().contains("key1"));
    assertTrue(multiStringsDictionary.getKeys().contains("key2"));
    assertFalse(multiStringsDictionary.getKeys().contains("key3"));
  }

  @Test
  public void testGetMembersKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiStringsDictionary.getMembers("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetMembersMembersNotFoundException()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiStringsDictionary.add("existingKey", "existingMember");
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiStringsDictionary.getMembers("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetMembers()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key1", "value2");
    Set<String> members = multiStringsDictionary.getMembers("key1");
    assertTrue(members.contains("value1"));
    assertTrue(members.contains("value2"));
  }

  @Test
  public void testRemoveMemberFromKeyKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiStringsDictionary.removeMemberFromKey("nonExistingKey", "value1");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveMemberFromKeyMembersNotFoundException()
      throws MemberAlreadyExistsException {
    multiStringsDictionary.add("existingKey", "value1");
    MembersNotFoundException exception =
        assertThrows(
            MembersNotFoundException.class,
            () -> {
              multiStringsDictionary.removeMemberFromKey("existingKey", "nonExistingValue");
            });
    assertEquals("Error, member does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveMemberFromKeySuccess()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiStringsDictionary.add("existingKey", "value1");
    multiStringsDictionary.add("existingKey", "value2");
    multiStringsDictionary.removeMemberFromKey("existingKey", "value1");
    assertTrue(multiStringsDictionary.getMap().containsKey("existingKey"));
    assertFalse(multiStringsDictionary.getMap().get("existingKey").contains("value1"));
    assertTrue(multiStringsDictionary.getMap().get("existingKey").contains("value2"));
  }

  @Test
  public void testRemoveMemberFromKeyRemoveLastMember()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiStringsDictionary.add("existingKey", "value1");
    multiStringsDictionary.removeMemberFromKey("existingKey", "value1");
    assertFalse(multiStringsDictionary.getMap().containsKey("existingKey"));
  }

  @Test
  public void testRemoveAllMemberOfKeyKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiStringsDictionary.removeAllMemberOfKey("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveAllMemberOfKeySuccess()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiStringsDictionary.add("existingKey", "value1");
    multiStringsDictionary.add("existingKey", "value2");
    multiStringsDictionary.removeAllMemberOfKey("existingKey");
    assertFalse(multiStringsDictionary.getMap().containsKey("existingKey"));
  }

  @Test
  public void testIsKeyExistsWhenKeyExists() throws MemberAlreadyExistsException {
    multiStringsDictionary.add("existingKey", "value1");
    boolean result = multiStringsDictionary.isKeyExists("existingKey");
    assertTrue(result);
  }

  @Test
  public void testIsKeyExistsWhenKeyDoesNotExist() {
    boolean result = multiStringsDictionary.isKeyExists("nonExistingKey");
    assertFalse(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenMemberExists()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiStringsDictionary.add("existingKey", "existingValue");
    boolean result =
        multiStringsDictionary.isMemberExistsWithinAKey("existingKey", "existingValue");
    assertTrue(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenMemberDoesNotExist()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiStringsDictionary.add("existingKey", "existingValue");
    boolean result =
        multiStringsDictionary.isMemberExistsWithinAKey("existingKey", "nonExistingValue");
    assertFalse(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenKeyDoesNotExist() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiStringsDictionary.isMemberExistsWithinAKey("nonExistingKey", "anyValue");
            });
    assertEquals("ERROR, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetAllMembersWhenNoMembersExist() {
    List<Set<String>> allMembers = multiStringsDictionary.getAllMembers();
    assertTrue(allMembers.isEmpty());
  }

  @Test
  public void testGetAllMembersWhenMembersExist() throws MemberAlreadyExistsException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key1", "value2");
    multiStringsDictionary.add("key2", "value3");
    List<Set<String>> allMembers = multiStringsDictionary.getAllMembers();
    assertEquals(2, allMembers.size());
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value1", "value2"))));
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value3"))));
  }

  @Test
  public void testGetAllMembersWhenSomeKeysHaveNoMembers() throws MemberAlreadyExistsException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key2", "value2");
    List<Set<String>> allMembers = multiStringsDictionary.getAllMembers();
    assertEquals(2, allMembers.size());
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value1"))));
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value2"))));
  }

  @Test
  public void testGetItemsWhenNoItemsExist() {
    List<String> items = multiStringsDictionary.getItems();
    assertTrue(items.isEmpty());
  }

  @Test
  public void testGetItemsWhenItemsExist() throws MemberAlreadyExistsException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key1", "value2");
    multiStringsDictionary.add("key2", "value3");
    List<String> items = multiStringsDictionary.getItems();
    assertEquals(3, items.size());
    assertTrue(items.contains("key1: value1"));
    assertTrue(items.contains("key1: value2"));
    assertTrue(items.contains("key2: value3"));
  }

  @Test
  public void testGetItemsWithEmptyKeys()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key2", "value2");
    multiStringsDictionary.removeAllMemberOfKey("key1");
    List<String> items = multiStringsDictionary.getItems();
    assertEquals(1, items.size());
    assertTrue(items.contains("key2: value2"));
  }

  @Test
  public void testClearMap() throws MemberAlreadyExistsException {
    multiStringsDictionary.add("key1", "value1");
    multiStringsDictionary.add("key2", "value2");
    multiStringsDictionary.clearMap();
    assertTrue(multiStringsDictionary.getMap().isEmpty());
  }

  @Test
  void testEquals() throws MemberAlreadyExistsException {
    MultiStringsDictionary dict1 = new MultiStringsDictionary();
    MultiStringsDictionary dict2 = new MultiStringsDictionary();
    MultiStringsDictionary dict3 = new MultiStringsDictionary();
    dict1.add("key1", "value1");
    dict1.add("key2", "value2");
    dict2.add("key1", "value1");
    dict2.add("key2", "value2");
    dict3.add("key1", "value1");
    assertEquals(dict1, dict1);
    assertEquals(dict1, dict2);
    assertEquals(dict2, dict1);

    assertNotEquals(dict1, dict3);
    assertNotEquals(dict1, null);
    assertNotEquals(dict1, "some string");
  }

  @Test
  void testHashCode() throws MemberAlreadyExistsException {
    MultiStringsDictionary dict1 = new MultiStringsDictionary();
    MultiStringsDictionary dict2 = new MultiStringsDictionary();
    MultiStringsDictionary dict3 = new MultiStringsDictionary();
    dict1.add("key1", "value1");
    dict1.add("key2", "value2");
    dict2.add("key1", "value1");
    dict2.add("key2", "value2");
    dict3.add("key1", "value1");

    assertEquals(dict1.hashCode(), dict1.hashCode());
    assertEquals(dict1.hashCode(), dict2.hashCode());
    assertNotEquals(dict1.hashCode(), dict3.hashCode());
  }
}
