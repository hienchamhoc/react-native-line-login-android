# react-native-line-login-android

Let your users sigin with their LINE account.

## Installation

```sh
npm install react-native-line-login-android
```
### Manual installation

1. Add jcenter() to android/build.gradle (if not already)
  ```sh
    allprojects {
      ...
        repositories{
          jcenter()
          ...
        }
      ...
    }
  ```
2. Change android:allowBackup to "true" in android/app/src/main/AndroidManifest.xml
## Usage

```js
import { Login } from 'react-native-line-login-android';

// ...

try {
  Login('#channelId')
    .then((result)=> {
      console.log(result);//string
      console.log(JSON.parse(result));//object
    })
}catch (err){
  console.log(err);
}
```

# Thank you!

