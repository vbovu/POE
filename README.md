# POE Part 2 Final Polished Pack

This pack contains the Part 2 files discussed:

- `ChatUp.java`
  - Remains the registration/login gateway.
  - Hands over to QuickChat only after successful login.

- `QuickChat.java`
  - Displays `Welcome to QuickChat.`
  - Uses a numeric menu with options 1–3.
  - Uses a `while` loop for the application menu.
  - Uses a `for` loop for the fixed number of messages chosen by the user.
  - Invalid recipient/message-length entries do not consume a valid message slot.

- `Message.java`
  - Generates unique ten-digit Message IDs during the current program run.
  - Validates recipient format by reusing `Registration.isValidCellPhoneNumber(...)`.
  - Checks the 250-character rule.
  - Builds the Message Hash with substring/string manipulation.
  - Uses ordinary `String` concatenation instead of `StringBuilder` to keep the Part 2 code visibly first-principles and marker-friendly.
  - Supports Send / Disregard / Store.
  - Stores messages in JSON with IEEE attribution and local numbered citation comments for the researched JSON/file operations.

- `MessageTest.java`
  - Covers the Part 2 message methods and key outputs.

- `pom.xml`
  - Uses Java 17 for stable compatibility with the arrow switch syntax and GitHub Actions.

- `.github/workflows/TestJava.yml`
  - Runs Maven tests automatically on push and pull request.
  - Also supports manual runs from the GitHub Actions tab through `workflow_dispatch`.

Place these files into the matching Maven project folders and keep your existing Part 1 `Messages.java`, `Login.java`, and `Registration.java` unless you later choose to refactor them for the final PoE.
