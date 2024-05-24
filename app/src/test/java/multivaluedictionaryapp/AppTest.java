package multivaluedictionaryapp;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static multivaluedictionaryapp.App.handleAddCommand;

public class AppTest {

  private final ByteArrayOutputStream sysOut = new ByteArrayOutputStream();
  private final ByteArrayOutputStream sysErr = new ByteArrayOutputStream();
  private MultiStringsDictionary dict;

  @BeforeEach
  void setUp() {
    System.setOut(new PrintStream(sysOut));
    System.setErr(new PrintStream(sysErr));
    dict = new MultiStringsDictionary();
  }

  @Test
  void testHandleAddCommandWithValidArguments() throws MemberAlreadyExistsException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"ADD", "key", "value"};
    App.handleAddCommand(dictMock, arguments);
    verify(dictMock).add("key", "value");
    assertEquals("Added", sysOut.toString().trim());
  }

  @Test
  void testHandleAddCommandWithMissingParameter() {

    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"ADD", "key"};
    App.handleAddCommand(dictMock, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'ADD key value'.", sysOut.toString().trim());
  }

  @Test
  void testHandleAddCommandMemberAlreadyExistsException() throws MemberAlreadyExistsException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"ADD", "key", "value"};
    doThrow(new MemberAlreadyExistsException("Error, member already exists for key."))
        .when(dictMock)
        .add("key", "value");
    handleAddCommand(dictMock, arguments);
    assertEquals("Error, member already exists for key.", sysErr.toString().trim());
  }

  @Test
  void handleKeysCommandEmptyDictionary() {
    String[] arguments = {"KEYS"};
    App.handleKeysCommand(dict, arguments);
    assertEquals("(empty set)", sysOut.toString().trim());
  }

  @Test
  void handleKeysCommandNonEmptyDictionary()
      throws MemberAlreadyExistsException, KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"MEMBERS", "key"};

    Set<String> values = new HashSet<>();
    values.add("value1");
    values.add("value2");
    when(dictMock.getMembers("key")).thenReturn(values);
    App.handleMembersExistCommand(dictMock, arguments);
  }

  @Test
  void testHandleKeysCommandWithKeys() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"KEYS"};
    Set<String> keys = new HashSet<>();
    keys.add("key1");
    keys.add("key2");
    when(dictMock.getKeys()).thenReturn(keys);
    App.handleKeysCommand(dictMock, arguments);
  }

  @Test
  void handleKeysCommandMoreParameters() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"KEYS", "extraParam"};
    App.handleKeysCommand(dictMock, arguments);
    assertEquals("Error, missing or redundant parameter, please use 'KEYS'.", sysOut.toString().trim());
  }

  @Test
  void testHandleMembersExistCommandWithKeyNotFoundException() throws KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"MEMBERS", "nonExistingKey"};

    doThrow(new KeyNotFoundException("Error, key does not exist."))
        .when(dictMock)
        .getMembers("nonExistingKey");
    App.handleMembersExistCommand(dictMock, arguments);
    assertEquals("Error, key does not exist.", sysErr.toString().trim());
  }

  @Test
  void testHandleMembersExistCommandWithEmptySet() throws KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"MEMBERS", "key"};
    when(dictMock.getMembers("key")).thenReturn(Collections.emptySet());
    App.handleMembersExistCommand(dictMock, arguments);
    assertEquals(App.EMPTY_SET_MESSAGE, sysOut.toString().trim());
  }

  @Test
  public void testHandleMembersExistCommandNoKey() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] argumentsWithoutKey = {"MEMBERS"};
    App.handleMembersExistCommand(dictMock, argumentsWithoutKey);
    assertEquals(
        "Error, missing or redundant parameter, please use 'MEMBERS key'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleClearCommandWithOneArgument() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = new String[] {"argument"};
    App.handleClearCommand(dictMock, arguments);
    verify(dictMock, times(1)).clearMap();
    assertEquals("Cleared", sysOut.toString().trim());
  }

  @Test
  public void testHandleClearCommandWithMultipleArguments() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] argumentsWithoutKey = {"CLEAR", "Key1"};
    App.handleClearCommand(dictMock, argumentsWithoutKey);
    assertEquals("Error, missing or redundant parameter, please use 'CLEAR'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleItemsCommandWithOneArgumentAndEmptyMap() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    when(dictMock.getMap()).thenReturn(Collections.emptyMap());
    String[] arguments = new String[] {"ITEMS"};
    App.handleItemsCommand(dictMock, arguments);
    assertEquals(App.EMPTY_SET_MESSAGE, sysOut.toString().trim());
  }

  @Test
  public void testHandleItemsCommandWithOneArgumentAndNonEmptyMap() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);

    List<String> list = new ArrayList<>(Arrays.asList("key1: value1", "key1: value2"));
    when(dictMock.getItems()).thenReturn(list);
    Map<String, Set<String>> map = new HashMap();
    map.put("key1", Set.of("value1", "value2"));
    when(dictMock.getMap()).thenReturn(map);
    String[] arguments = new String[] {"ITEMS"};
    App.handleItemsCommand(dictMock, arguments);
  }

  @Test
  public void testHandleItemsCommandWithMultipleArguments() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] argumentsWithoutKey = {};
    App.handleItemsCommand(dictMock, argumentsWithoutKey);
    assertEquals("Error, missing or redundant parameter, please use 'ITEMS'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleAllMembersCommandWithOneArgumentAndEmptyMap() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    when(dictMock.getMap()).thenReturn(Collections.emptyMap());
    String[] arguments = new String[] {"ALLMEMBERS"};
    App.handleAllMembersCommand(dictMock, arguments);
    assertEquals(App.EMPTY_SET_MESSAGE, sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveCommandWithExistingKeyValuePairAndNonEmptyMap()
      throws KeyNotFoundException, MembersNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);

    Map<String, Set<String>> map = new HashMap();
    map.put("key1", Set.of("value2"));
    map.put("key2", Set.of("value3", "value4"));
    doNothing().when(dictMock).removeMemberFromKey("key1", "value1");
    String[] arguments = new String[] {"REMOVE", "key1", "value1"};
    App.handleRemoveCommand(dictMock, arguments);
    assertEquals("Removed", sysOut.toString().trim());
  }

  @Test
  public void testHandleAllMembersCommandAndNonEmptyMap()
      throws KeyNotFoundException, MembersNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    List<Set<String>> setList = new ArrayList<>();
    setList.add(Set.of("value3"));
    setList.add(Set.of("value3"));
    when(dictMock.getAllMembers()).thenReturn(setList);
    String[] arguments = new String[] {"ALLMEMBERS"};
    App.handleAllMembersCommand(dictMock, arguments);
  }

  @Test
  public void testHandleAllMembersCommandWithMultipleArguments() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"ALLMEMBERS", "value1"};
    App.handleAllMembersCommand(dictMock, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'ALLMEMBERS'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveCommandCommandWithMultipleArguments() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"REMOVE", "key1", "value1", "key2", "value2"};
    App.handleRemoveCommand(dictMock, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'REMOVE key value'.",
        sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveCommandWithOneArgumentAndNonEmptyMap()
      throws KeyNotFoundException, MembersNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    Map<String, Set<String>> map = new HashMap<>();
    map.put("key1", Set.of("value2"));
    map.put("key2", Set.of("value3", "value4"));
    when(dictMock.getMap()).thenReturn(map);

    String[] arguments = new String[] {"REMOVE", "key1", "value1"};
    App.handleRemoveCommand(dictMock, arguments);
    verify(dictMock)
        .removeMemberFromKey(ArgumentMatchers.eq("key1"), ArgumentMatchers.eq("value1"));
    assertEquals("Removed", sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveCommandKeyNotFoundException()
      throws KeyNotFoundException, MembersNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = new String[] {"REMOVE", "key", "value"};
    doThrow(new KeyNotFoundException("Error, key does not exist."))
        .when(dictMock)
        .removeMemberFromKey("key", "value");
    App.handleRemoveCommand(dictMock, arguments);
    assertEquals(
        String.format(MultiStringsDictionary.NOT_EXIST_ERROR_MESSAGE_TEMPLATE, "key"),
        sysErr.toString().trim());
  }

  @Test
  public void testhandleMemberExistsCommandWithMultipleArguments()
      throws KeyNotFoundException, KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"MEMBERS"};
    App.handleMemberExistsCommand(dictMock, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'MEMBEREXISTS key value'.",
        sysOut.toString().trim());
  }

  @Test
  public void testHandleMemberExistsCommand() throws KeyNotFoundException, KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"MEMBEREXISTS", "key1", "value1"};
    when(dictMock.isMemberExistsWithinAKey("key1", "value1")).thenReturn(true);
    App.handleMemberExistsCommand(dictMock, arguments);
    assertEquals("true", sysOut.toString().trim());
    when(dictMock.isMemberExistsWithinAKey("key1", "value1"))
        .thenThrow(new KeyNotFoundException("Key not found"));
    App.handleMemberExistsCommand(dictMock, arguments);
    assertEquals("Key not found", sysErr.toString().trim());
  }

  @Test
  public void testHandleKeyExistsCommandWithEmptySet() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"KEYEXISTS", "key1"};
    when(dictMock.isKeyExists("key1")).thenReturn(true);
    App.handleKeyExistsCommand(dictMock, arguments);
    assertEquals("true", sysOut.toString().trim());
  }

  @Test
  public void testHandleKeyExistsCommand() {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"KEYEXISTS key1 key2"};
    App.handleKeyExistsCommand(dictMock, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'KEYEXISTS key'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveAllWithMissingArguments() {
    String[] arguments = {"REMOVEALL"};
    App.handleRemoveAllCommand(dict, arguments);
    assertEquals(
        "Error, missing or redundant parameter, please use 'REMOVEALL key'.", sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveAllCommandSuccess() throws KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"REMOVEALL", "key1"};
    App.handleRemoveAllCommand(dictMock, arguments);
    verify(dictMock, times(1)).removeAllMemberOfKey("key1");
    assertEquals("Removed", sysOut.toString().trim());
  }

  @Test
  public void testHandleRemoveAllCommandWithKeyNotExisting() throws KeyNotFoundException {
    MultiStringsDictionary dictMock = mock(MultiStringsDictionary.class);
    String[] arguments = {"REMOVEALL", "key1"};
    App.handleRemoveAllCommand(dictMock, arguments);
    doThrow(new KeyNotFoundException("Error, key does not exist."))
        .when(dictMock)
        .removeAllMemberOfKey("key1");
    App.handleRemoveAllCommand(dictMock, arguments);
    assertEquals("Error, key does not exist.", sysErr.toString().trim());
  }
}
