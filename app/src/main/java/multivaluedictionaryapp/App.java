package multivaluedictionaryapp;

import java.util.Scanner;
import java.util.Set;

import com.google.common.annotations.VisibleForTesting;

public class App {

  protected static final String MISSING_OR_REDUNDANT_PARAMETER_ERROR =
      "Error, missing or redundant parameter, please use '%s'.";
  protected static final String EMPTY_SET_MESSAGE = "(empty set)";

  public static void main(String[] args) {

    Scanner scanner = new Scanner(System.in);
    System.out.println("Welcome to the APP！");
    MultiStringsDictionary dict = new MultiStringsDictionary();

    while (true) {
      String input = scanner.nextLine().trim();
      if (input.equalsIgnoreCase("EXIT")) {
        System.out.println("Exiting the application.");
        scanner.close();
        return;
      }

      String[] arguments = input.split("\\s+");
      if (arguments.length == 0) {
        continue;
      }

      String command = arguments[0].toUpperCase();
      switch (command) {
        case "ADD":
          handleAddCommand(dict, arguments);
          break;
        case "KEYS":
          handleKeysCommand(dict, arguments);
          break;
        case "MEMBERS":
          handleMembersExistCommand(dict, arguments);
          break;
        case "REMOVE":
          handleRemoveCommand(dict, arguments);
          break;
        case "REMOVEALL":
          handleRemoveAllCommand(dict, arguments);
          break;
        case "CLEAR":
          handleClearCommand(dict, arguments);
          break;
        case "KEYEXISTS":
          handleKeyExistsCommand(dict, arguments);
          break;
        case "MEMBEREXISTS":
          handleMemberExistsCommand(dict, arguments);
          break;
        case "ALLMEMBERS":
          handleAllMembersCommand(dict, arguments);
          break;
        case "ITEMS":
          handleItemsCommand(dict, arguments);
          break;
        default:
          System.out.println(
              "ERROR: Unknown command. Please use a valid command or type EXIT to quit.");
          break;
      }
    }
  }

  @VisibleForTesting
  protected static void handleKeysCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 1) {
      Set<String> keys = dict.getKeys();
      if (keys.isEmpty()) {
        System.out.println(EMPTY_SET_MESSAGE);
      } else {
        int index = 1;
        for (String key : keys) {
          System.out.println(String.format("%d) %s", index++, key));
        }
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "KEYS"));
    }
  }

  @VisibleForTesting
  protected static void handleMembersExistCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 2) {
      String key = arguments[1];
      try {
        Set<String> values = dict.getMembers(key);
        if (values.isEmpty()) {
          System.out.println(EMPTY_SET_MESSAGE);
        } else {
          int index = 1;
          for (String value : values) {
            System.out.println(index++ + ") " + value);
          }
        }
      } catch (KeyNotFoundException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "MEMBERS key"));
    }
  }

  @VisibleForTesting
  protected static void handleAddCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 3) {
      String key = arguments[1];
      String value = arguments[2];
      try {
        dict.add(key, value);
        System.out.println("Added");
      } catch (MemberAlreadyExistsException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "ADD key value"));
    }
  }

  @VisibleForTesting
  protected static void handleRemoveCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 3) {
      String key = arguments[1];
      String value = arguments[2];
      try {
        dict.removeMemberFromKey(key, value);
        System.out.println("Removed");
      } catch (KeyNotFoundException | MembersNotFoundException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "REMOVE key value"));
    }
  }

  @VisibleForTesting
  protected static void handleRemoveAllCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 2) {
      String key = arguments[1];
      try {
        dict.removeAllMemberOfKey(key);
        System.out.println("Removed");
      } catch (KeyNotFoundException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "REMOVEALL key"));
    }
  }

  @VisibleForTesting
  protected static void handleClearCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 1) {
      dict.clearMap();
      System.out.println("Cleared");

    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "CLEAR"));
    }
  }

  @VisibleForTesting
  protected static void handleKeyExistsCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 2) {
      String key = arguments[1];
      System.out.println(dict.isKeyExists(key));
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "KEYEXISTS key"));
    }
  }

  @VisibleForTesting
  protected static void handleMemberExistsCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 3) {
      String key = arguments[1];
      String value = arguments[2];
      try {
        System.out.println(dict.isMemberExistsWithinAKey(key, value));
      } catch (KeyNotFoundException e) {
        System.err.println(e.getMessage());
      }
    } else {
      System.out.println(
          String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "MEMBEREXISTS key value"));
    }
  }

  @VisibleForTesting
  protected static void handleAllMembersCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 1) {
      if (dict.getAllMembers().isEmpty()) {
        System.out.println(EMPTY_SET_MESSAGE);
      }
      int index = 1;
      for (Set<String> set : dict.getAllMembers()) {
        for (String member : set) {
          System.out.println(String.format("%d) %s", index++, member));
        }
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "ALLMEMBERS"));
    }
  }

  @VisibleForTesting
  protected static void handleItemsCommand(MultiStringsDictionary dict, String[] arguments) {
    if (arguments.length == 1) {
      if (dict.getMap().isEmpty()) {
        System.out.println(EMPTY_SET_MESSAGE);
      }
      int index = 1;
      for (String itemString : dict.getItems()) {
        System.out.println(String.format("%d) %s", index++, itemString));
      }
    } else {
      System.out.println(String.format(MISSING_OR_REDUNDANT_PARAMETER_ERROR, "ITEMS"));
    }
  }
}
