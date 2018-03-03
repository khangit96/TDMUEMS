// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.

export const environment = {
  production: false,
  firebase: {
    apiKey: "AIzaSyC0toT5xZYsVnWCcBBq5C5uqPea6vd0yP8",
    authDomain: "smarthome-5d11a.firebaseapp.com",
    databaseURL: "https://smarthome-5d11a.firebaseio.com",
    projectId: "smarthome-5d11a",
    storageBucket: "smarthome-5d11a.appspot.com",
    messagingSenderId: "41399729504"
  },
  dialogflow: {
    angularBot: '5da52399d5b14bcda14f3f091e0f5f13'
  }
};
