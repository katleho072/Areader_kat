package com.example.areader.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.areader.R
import com.example.areader.components.EmailInput
import com.example.areader.components.PasswordInput
import com.example.areader.components.ReaderLogo
import com.example.areader.navigation.ReaderScreens


@Composable
fun ReaderLoginScreen(navController: NavHostController, viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
      val showLoginForm = rememberSaveable { mutableStateOf(true) }

         Surface(modifier = Modifier.fillMaxSize()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top) {
                ReaderLogo()
                if (showLoginForm.value) UserForm(loading = false, isCreatedAccount = false){ email, password ->
                    //Todo Fb login
                    viewModel.signInWithEmailAndPassword(email, password){
                        navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                    }
                }
                else {
                    UserForm(loading = false, isCreatedAccount = true){ email, password ->
                        // Todo: create Fb account
                        viewModel.createUserWithEmailAndPassword(email, password){
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        }

                    }
                }
            }
             Spacer(modifier = Modifier.height(15.dp))
              Row (
                  modifier = Modifier.padding(15.dp),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically
              ){
                  val text = if (showLoginForm.value) "Sign up" else "Login"
                  Text(text = "New User")
                  Text(text = text,
                      modifier = Modifier
                          .clickable {
                              showLoginForm.value = !showLoginForm.value
                          }
                          .padding(start = 5.dp),
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colorScheme.secondaryContainer)


              }


        }
}

@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(
   loading: Boolean = false,
   isCreatedAccount: Boolean = false,
   onDone: (String, String) -> Unit = {email, pswd ->}
){
   val email = rememberSaveable {
       mutableStateOf("")
    }
 val password = rememberSaveable {
     mutableStateOf("")
 }
 val passwordVisibility = rememberSaveable {
    mutableStateOf(false)
 }
val passwordFocusRequest = FocusRequester.Default

    //val passwordFocusRequest = remember { FocusRequester() }

    val keyboardController = LocalSoftwareKeyboardController.current

val valid = remember(email.value, password.value) {
   email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()

}

val modifier = Modifier
    .height(250.dp)
    .background(MaterialTheme.colorScheme.background)
    .verticalScroll(rememberScrollState())
  Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
      if (isCreatedAccount) Text(text = stringResource(id = R.string.create_acc),
          modifier = Modifier.padding(4.dp)) else Text(text = "")
     EmailInput(emailState = email,
         enabled = !loading,
         onAction = KeyboardActions {
          passwordFocusRequest.requestFocus()
      })

      PasswordInput(
          modifier = Modifier.focusRequester(passwordFocusRequest),
          passwordState = password,
          labelId = "Password",
          enabled = !loading, // Todo change this
          passwordVisibility = passwordVisibility,
          onAction = KeyboardActions {

              if (!valid) return@KeyboardActions
              onDone(email.value, password.value.trim())

          }
      )

      SubmitButton(
          textId = if (isCreatedAccount) "Create Account " else "Login",
          loading = loading,
          validInputs = valid
      ){
          onDone(email.value.trim(), password.value.trim())
          keyboardController!!.hide()
      }

   }


 }

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
      Button(onClick = onClick,
          modifier = Modifier
              .padding(3.dp)
              .fillMaxWidth(),
              enabled = !loading && validInputs,
             shape = CircleShape
          ) {
         if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
          else Text(text = textId, modifier = Modifier.padding(5.dp))
      }

}




