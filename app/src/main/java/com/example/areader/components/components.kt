package com.example.areader.components

import android.content.Context
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.areader.R
import com.example.areader.model.MBook
import com.example.areader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ReaderLogo (modifier: Modifier = Modifier) {
    Text(
        text = "A. Reader ", modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.titleLarge,
        color = Color.Red.copy(alpha = 0.5f)
    )
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(value = valueState.value,
        onValueChange = {valueState.value = it},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = Color.Black),
        modifier = modifier
            .padding(
                top = 10.dp,
                bottom = 10.dp,
                start = 10.dp,
                end = 10.dp
            )
            .fillMaxWidth(),
        enabled = enabled,
        label = { Text(text = labelId) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction
        ), keyboardActions = onAction
    )

}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
){
    InputField(
        modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )

}


@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  labelId: String,
                  enabled: Boolean,
                  passwordVisibility: MutableState<Boolean>,
                  imeAction: ImeAction = ImeAction.Done,
                  onAction: KeyboardActions = KeyboardActions.Default,
) {

    val visualTransformation = if (passwordVisibility.value)
        VisualTransformation.None else PasswordVisualTransformation()
    OutlinedTextField(value = passwordState.value, onValueChange = {
        passwordState.value = it
    },
        label = { Text(text = labelId)},
        singleLine =  true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction
    )

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible}) {
        Icons.Default.Close

    }

}

@Composable
fun TitleSection(modifier: Modifier = Modifier,
                 label: String){
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left)

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked:()-> Unit = {}
){
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile){
                    Image(painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "icon",
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .scale(0.6f))
                }
                if (icon != null){
                    Icon(imageVector = icon, contentDescription = "arrow back",
                        tint = Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.clickable { onBackArrowClicked.invoke() })
                }
                Spacer(modifier = Modifier.width(30.dp))
                Text(text = title,
                    color = Color.Red.copy(alpha = 0.7f),
                    style = TextStyle(fontWeight = FontWeight.Bold,
                        fontSize = 20.sp)
                )
                Spacer(modifier = Modifier.width(150.dp))
            }
        },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }){
                if (showProfile) Row {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Logout,
                        contentDescription = "Logout"
                    )
                } else Box {

                }


            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            Color.Transparent
        ),
    )


}

@Composable
fun FabContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = {
        onTap()
    },
        shape = RoundedCornerShape(50.dp),
        containerColor = Color.Blue,
        contentColor = Color.Blue
    ) {
        Icon(imageVector = Icons.Default.Add,
            contentDescription = "Add a Book",
            tint = Color.White)

    }


}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface (modifier = Modifier
        .height(70.dp)
        .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp,
        color = Color.White
    ){
        Column(modifier = Modifier.padding(4.dp)) {
            Icon(imageVector = Icons.Filled.StarBorder,
                contentDescription = "Start",
                modifier = Modifier.padding(3.dp))
            Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)

        }

    }

}



@Composable
fun ListCard(book: MBook,
             onPressedDetails: (String) -> Unit = {}){

    val  context = LocalContext.current
    val resources = context.resources

    val displayMetrics = resources.displayMetrics

    val spacing = 10.dp

    val screenWidth = displayMetrics.widthPixels / displayMetrics.density



    Card(shape = RoundedCornerShape(29.dp),
        colors = CardDefaults.cardColors(
            Color.White
        ),
        elevation = CardDefaults.cardElevation(
            6.dp
        ),
        modifier = Modifier
            .padding(16.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable { onPressedDetails.invoke(book.title.toString()) }
    ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2)),
            horizontalAlignment = Alignment.Start) {
            Row (horizontalArrangement = Arrangement.Center){
                Image(painter = rememberAsyncImagePainter(model = book.photoUrl.toString()),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp))
                Spacer(modifier = Modifier.width(50.dp))
                Column(modifier = Modifier.padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                        modifier = Modifier.padding(bottom = 1.dp))
                    BookRating(score = book.rating!!)



                }

            }
            Text(text = book.title.toString(), modifier = Modifier.padding(4.dp),
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis) //...

            Text(text = book.authors.toString(), modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelSmall)

        }

        val isStartedReading = remember {
            mutableStateOf(false)
        }

        Row(  modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom) {
            isStartedReading.value = book.startedReading!= null

            RoundedButton(label = if (isStartedReading.value) "Reading" else "Not Yet", radius = 70)

        }
    }

}



@Preview
@Composable
fun RoundedButton(label: String = "Reading",
                  radius: Int = 29,
                  cornerRadius: Dp = radius.dp,
                  onPress: () -> Unit = {}){
    Surface(modifier = Modifier.clip(RoundedCornerShape(
        bottomEndPercent = radius,
        topStartPercent = radius
    )),
        color = Color(0xFF92CBDF)
    ) {
        Column(modifier = Modifier
            .width(90.dp)
            .height(40.dp)
            .clickable {
                onPress.invoke()
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = TextStyle(color = Color.White,
                fontSize = 15.sp))

        }
    }

}


// Rating Bar

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onPressRating: (Int) -> Unit
){
    var ratingState by remember {
        mutableStateOf(rating)
    }
    
    var selected by remember {
        mutableStateOf(false)
    }
    
    
    val size by animateDpAsState(targetValue = if (selected) 42.dp else 34.dp,
        spring(Spring.DampingRatioMediumBouncy), label = ""
    )
    
  
  Row(
      modifier = Modifier.width(280.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
  ) {
      for (i in 1..5){
       Icon(painter = painterResource(id = R.drawable.star ),
           contentDescription = "star",
           modifier = modifier
               .width(size)
               .height(size)
               .pointerInteropFilter {
                   when (it.action) {
                       MotionEvent.ACTION_DOWN -> {
                           selected = true
                           onPressRating(i)
                           ratingState = i
                       }

                       MotionEvent.ACTION_UP -> {
                           selected = false
                       }
                   }
                   true
               },
           tint = if (i <= ratingState) Color(0xFFFFD700) else Color(0xFFA2ADB1)
       )
      }
      
  }  
    
}

fun showToast(context: Context, msg: String){
    Toast.makeText(context, msg, Toast.LENGTH_LONG)
        .show()
}



