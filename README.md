# ZapTel

ZapTel is an app that can manager users using an API and manages contacts.

## IDE

[AndroidStudio](https://developer.android.com/studio) ver. 3.5.1

## Dependencies

[gson](https://github.com/google/gson) ver. 2.8.5

[volley](https://github.com/google/volley) ver. 1.1.1

## Specifications

**minSdkVersion 21**

**targetSdkVersion 28**

## How to use

#### Login Screen
```bash
The first screen contains two fields, for the user email and the user password. This information is necessary to log in to the app and go to Contacts screen.
It also contains a checkbox to remember this information for the next time of use.
The user can click at the button Create User to go to the Create User screen.
```

#### Create User Screen
```bash
At this screen, the user can fill all the information and then create a user with it.
```

#### Contacts Screen
```bash
The Contacts screen contains a list of all contacts created by the user orderly alphabetical.
Each contact has a button to delete it.
There's also a button at bottom of the screen to add a new contact when this button is used, a dialog appears and then the user can create a contact.
```

#### Edit User Screen
```bash
At this screen, all the information of the user is shown, and the user can modify this information and save it using the button Save User.
The user can also deactivate his account, using the button Deactivate User, and for it, it's necessary to confirm with the password.
It also contains the Logout Button that log out the user and return for the first screen.
```