package multivaluedictionaryapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class MultiStringsDictionary {

  protected static final String NOT_EXIST_ERROR_MESSAGE_TEMPLATE = "Error, %s does not exist.";
  protected static final String MEMBER_EXISTS_FOR_KEY_ERROR_MESSAGE =
      "Error, member already exists for key.";
  private final Map<String, Set<String>> map;

  public MultiStringsDictionary() {
    map = new HashMap<>();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MultiStringsDictionary that = (MultiStringsDictionary) o;
    return Objects.equals(map, that.map);
  }

  @Override
  public int hashCode() {
    return Objects.hash(map);
  }

  // Helper function to get the whole hashmap
  public Map<String, Set<String>> getMap() {
    return map;
  }

  // ADD
  public void add(String key, String value) throws MemberAlreadyExistsException {
    if (map.containsKey(key) && map.get(key).contains(value)) {
      throw new MemberAlreadyExistsException(MEMBER_EXISTS_FOR_KEY_ERROR_MESSAGE);
    } else {
      Set<String> set = map.computeIfAbsent(key, k -> new HashSet<>());
      set.add(value);
    }
  }

  // KEYS
  public Set<String> getKeys() {
    return map.keySet();
  }

  // MEMBERS
  public Set<String> getMembers(String key) throws KeyNotFoundException {
    if (!map.containsKey(key)) {
      throw new KeyNotFoundException(String.format(NOT_EXIST_ERROR_MESSAGE_TEMPLATE, "key"));
    }
    Set<String> values = map.get(key);
    return values;
  }

  // REMOVE
  public void removeMemberFromKey(String key, String value)
      throws KeyNotFoundException, MembersNotFoundException {
    if (!map.containsKey(key)) {
      throw new KeyNotFoundException(String.format(NOT_EXIST_ERROR_MESSAGE_TEMPLATE, "key"));
    }
    if (!map.get(key).contains(value)) {
      throw new MembersNotFoundException(String.format(NOT_EXIST_ERROR_MESSAGE_TEMPLATE, "member"));
    } else {
      if (map.get(key).size() == 1) {
        map.remove(key);
      } else {
        map.get(key).remove(value);
      }
    }
  }

  // REMOVEALL
  public void removeAllMemberOfKey(String key) throws KeyNotFoundException {
    if (!map.containsKey(key)) {
      throw new KeyNotFoundException(String.format(NOT_EXIST_ERROR_MESSAGE_TEMPLATE, "key"));
    }
    map.remove(key);
  }

  // CLEAR
  public void clearMap() {
    map.clear();
  }

  // KEYEXISTS
  public boolean isKeyExists(String key) {
    return map.containsKey(key);
  }

  // MEMBEREXISTS
  public boolean isMemberExistsWithinAKey(String key, String value) throws KeyNotFoundException {
    if (!map.containsKey(key)) {
      throw new KeyNotFoundException("ERROR, key does not exist.");
    }
    return map.get(key).contains(value);
  }

  // ALLMEMBERS
  public List<Set<String>> getAllMembers() {
    return new ArrayList<>(map.values());
  }

  // ITEMS
  public List<String> getItems() {
    List<String> resultList = new ArrayList<>();
    for (Map.Entry<String, Set<String>> item : map.entrySet()) {
      String tmpKey = item.getKey();
      for (String value : item.getValue()) {
        resultList.add(tmpKey + ": " + value);
      }
    }
    return resultList;
  }
}
