package com.example.areader.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.areader.components.FabContent
import com.example.areader.components.ListCard
import com.example.areader.components.ReaderAppBar
import com.example.areader.components.TitleSection
import com.example.areader.model.MBook
import com.example.areader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")

@Composable
fun Home(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
){
    Scaffold(
        topBar = {
                 ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {

       FabContent{
                navController.navigate(ReaderScreens.SearchScreen.name)

            }
        }, content = {
            innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        HomeContent(navController, viewModel)

                    }

                }

            
        }
    )

}

@Composable
fun FABContent(content: () -> Unit) {

}


@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel){

    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser
    if (!viewModel.data.value.data.isNullOrEmpty()){
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
            mBook.userId == currentUser?.uid.toString()
        }
    }

//    var listOfBooks = listOf(
//        MBook(id = "dadfa", title = "Millions",authors = "katleho", notes = "im a millionaire"),
//        MBook(id = "dadfa", title = "Million",authors = "blessing", notes = "im a millionaire"),
//        MBook(id = "dadfa", title = "Millionss",authors = "family", notes = "im a millionaire"),
//        MBook(id = "dadfa", title = "Million",authors = "kat", notes = "im a millionaire"),
//        MBook(id = "dadfa", title = "Millions",authors = "francina", notes = "im a millionaire")
//    )

    val currentUserName =if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)else "N/A"

    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {
        Row(Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n "+ "activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colorScheme.secondaryContainer)
                Text(text = currentUserName,
                modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Red,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()

            }


        }
        ReadingRightNowArea(listOfBooks = listOf(),
            navController = navController )

        TitleSection(label = "Reading List")

        BookListArea(lisOfBooks = listOfBooks,
            navController = navController)

    }


}

@Composable
fun BookListArea(lisOfBooks: List<MBook>,
                 navController: NavController) {

    val addedBooks = lisOfBooks.filter { mBook->
        mBook.startedReading == null && mBook.finishedReading == null
    }


    HorizontalScrollableComponent(lisOfBooks){
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }

}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row (modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)){
        if (viewModel.data.value.loading == true){
            LinearProgressIndicator()
        }else {
            if (listOfBooks.isNullOrEmpty()){
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found. Add a Book", style = TextStyle(
                        color = Color.Red.copy(alpha = 0.4f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    ))

                }
            }else { for (book in listOfBooks){
                ListCard(book){
                    onCardPressed(book.googleBookId.toString())

                }
            }

            }
        }



    }

}


@Composable
fun  ReadingRightNowArea(listOfBooks: List<MBook>,
                         navController: NavController){
    val readingNowList = listOfBooks.filter {mBook ->
        mBook.startedReading != null && mBook.finishedReading == null

    }

    HorizontalScrollableComponent(readingNowList ){
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }


    //ListCard()

}


