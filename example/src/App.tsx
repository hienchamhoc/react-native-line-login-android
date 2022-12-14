import * as React from 'react';

import { StyleSheet, View, Text, Button } from 'react-native';
import {Login } from 'react-native-line-login-android';

export default function App() {

  const login = () =>{
    Login('1657697411')
      .then((result)=>{
        console.log(result);
        console.log(typeof (result));//string
        console.log(JSON.parse(result));
        console.log(typeof (JSON.parse(result)));//object
      })

  }

  return (
    <View style={styles.container}>
      <Button
        title='Login'
        onPress={login}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
  box: {
    width: 60,
    height: 60,
    marginVertical: 20,
  },
});
