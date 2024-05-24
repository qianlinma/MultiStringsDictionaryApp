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

public class MultiValueDictionaryTest {

  private MultiValueDictionary multiValueDictionary;

  @BeforeEach
  public void setUp() {
    multiValueDictionary = new MultiValueDictionary();
  }

  @Test
  void testAddNewKeyAndValue() {
    try {
      multiValueDictionary.add("key1", "value1");
    } catch (MemberAlreadyExistsException e) {
      assertEquals("Error, member already exists for key.", e.getMessage());
    }
    assertTrue(multiValueDictionary.getMap().containsKey("key1"));
    assertTrue(multiValueDictionary.getMap().get("key1").contains("value1"));
  }

  @Test
  void testAddExistingKeyAndValue() {
    try {
      multiValueDictionary.add("key2", "value2");
      multiValueDictionary.add("key2", "value2");
    } catch (MemberAlreadyExistsException e) {
      assertEquals("Error, member already exists for key.", e.getMessage());
      assertTrue(multiValueDictionary.getMap().containsKey("key2"));
      assertTrue(multiValueDictionary.getMap().get("key2").contains("value2"));
      assertTrue(multiValueDictionary.getMap().size() == 1);
    }
  }

  @Test
  void testGetKeys() throws MemberAlreadyExistsException {
    assertTrue(multiValueDictionary.getKeys().isEmpty());
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key2", "value1");
    assertTrue(multiValueDictionary.getKeys().size() == 2);
    assertTrue(multiValueDictionary.getKeys().contains("key1"));
    assertTrue(multiValueDictionary.getKeys().contains("key2"));
    assertFalse(multiValueDictionary.getKeys().contains("key3"));
  }

  @Test
  public void testGetMembersKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiValueDictionary.getMembers("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetMembersMembersNotFoundException()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiValueDictionary.add("existingKey", "existingMember");
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiValueDictionary.getMembers("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetMembers()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key1", "value2");
    Set<String> members = multiValueDictionary.getMembers("key1");
    assertTrue(members.contains("value1"));
    assertTrue(members.contains("value2"));
  }

  @Test
  public void testRemoveMemberFromKeyKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiValueDictionary.removeMemberFromKey("nonExistingKey", "value1");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveMemberFromKeyMembersNotFoundException()
      throws MemberAlreadyExistsException {
    multiValueDictionary.add("existingKey", "value1");
    MembersNotFoundException exception =
        assertThrows(
            MembersNotFoundException.class,
            () -> {
              multiValueDictionary.removeMemberFromKey("existingKey", "nonExistingValue");
            });
    assertEquals("Error, member does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveMemberFromKeySuccess()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiValueDictionary.add("existingKey", "value1");
    multiValueDictionary.add("existingKey", "value2");
    multiValueDictionary.removeMemberFromKey("existingKey", "value1");
    assertTrue(multiValueDictionary.getMap().containsKey("existingKey"));
    assertFalse(multiValueDictionary.getMap().get("existingKey").contains("value1"));
    assertTrue(multiValueDictionary.getMap().get("existingKey").contains("value2"));
  }

  @Test
  public void testRemoveMemberFromKeyRemoveLastMember()
      throws MemberAlreadyExistsException, KeyNotFoundException, MembersNotFoundException {
    multiValueDictionary.add("existingKey", "value1");
    multiValueDictionary.removeMemberFromKey("existingKey", "value1");
    assertFalse(multiValueDictionary.getMap().containsKey("existingKey"));
  }

  @Test
  public void testRemoveAllMemberOfKeyKeyNotFoundException() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiValueDictionary.removeAllMemberOfKey("nonExistingKey");
            });
    assertEquals("Error, key does not exist.", exception.getMessage());
  }

  @Test
  public void testRemoveAllMemberOfKeySuccess()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiValueDictionary.add("existingKey", "value1");
    multiValueDictionary.add("existingKey", "value2");
    multiValueDictionary.removeAllMemberOfKey("existingKey");
    assertFalse(multiValueDictionary.getMap().containsKey("existingKey"));
  }

  @Test
  public void testIsKeyExistsWhenKeyExists() throws MemberAlreadyExistsException {
    multiValueDictionary.add("existingKey", "value1");
    boolean result = multiValueDictionary.isKeyExists("existingKey");
    assertTrue(result);
  }

  @Test
  public void testIsKeyExistsWhenKeyDoesNotExist() {
    boolean result = multiValueDictionary.isKeyExists("nonExistingKey");
    assertFalse(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenMemberExists()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiValueDictionary.add("existingKey", "existingValue");
    boolean result =
        multiValueDictionary.isMemberExistsWithinAKey("existingKey", "existingValue");
    assertTrue(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenMemberDoesNotExist()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiValueDictionary.add("existingKey", "existingValue");
    boolean result =
        multiValueDictionary.isMemberExistsWithinAKey("existingKey", "nonExistingValue");
    assertFalse(result);
  }

  @Test
  public void testIsMemberExistsWithinAKeyWhenKeyDoesNotExist() {
    KeyNotFoundException exception =
        assertThrows(
            KeyNotFoundException.class,
            () -> {
              multiValueDictionary.isMemberExistsWithinAKey("nonExistingKey", "anyValue");
            });
    assertEquals("ERROR, key does not exist.", exception.getMessage());
  }

  @Test
  public void testGetAllMembersWhenNoMembersExist() {
    List<Set<String>> allMembers = multiValueDictionary.getAllMembers();
    assertTrue(allMembers.isEmpty());
  }

  @Test
  public void testGetAllMembersWhenMembersExist() throws MemberAlreadyExistsException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key1", "value2");
    multiValueDictionary.add("key2", "value3");
    List<Set<String>> allMembers = multiValueDictionary.getAllMembers();
    assertEquals(2, allMembers.size());
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value1", "value2"))));
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value3"))));
  }

  @Test
  public void testGetAllMembersWhenSomeKeysHaveNoMembers() throws MemberAlreadyExistsException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key2", "value2");
    List<Set<String>> allMembers = multiValueDictionary.getAllMembers();
    assertEquals(2, allMembers.size());
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value1"))));
    assertTrue(allMembers.contains(new HashSet<>(Set.of("value2"))));
  }

  @Test
  public void testGetItemsWhenNoItemsExist() {
    List<String> items = multiValueDictionary.getItems();
    assertTrue(items.isEmpty());
  }

  @Test
  public void testGetItemsWhenItemsExist() throws MemberAlreadyExistsException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key1", "value2");
    multiValueDictionary.add("key2", "value3");
    List<String> items = multiValueDictionary.getItems();
    assertEquals(3, items.size());
    assertTrue(items.contains("key1: value1"));
    assertTrue(items.contains("key1: value2"));
    assertTrue(items.contains("key2: value3"));
  }

  @Test
  public void testGetItemsWithEmptyKeys()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key2", "value2");
    multiValueDictionary.removeAllMemberOfKey("key1");
    List<String> items = multiValueDictionary.getItems();
    assertEquals(1, items.size());
    assertTrue(items.contains("key2: value2"));
  }

  @Test
  public void testClearMap() throws MemberAlreadyExistsException {
    multiValueDictionary.add("key1", "value1");
    multiValueDictionary.add("key2", "value2");
    multiValueDictionary.clearMap();
    assertTrue(multiValueDictionary.getMap().isEmpty());
  }

  @Test
  void testEquals() throws MemberAlreadyExistsException {
    MultiValueDictionary dict1 = new MultiValueDictionary();
    MultiValueDictionary dict2 = new MultiValueDictionary();
    MultiValueDictionary dict3 = new MultiValueDictionary();
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
    MultiValueDictionary dict1 = new MultiValueDictionary();
    MultiValueDictionary dict2 = new MultiValueDictionary();
    MultiValueDictionary dict3 = new MultiValueDictionary();
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
