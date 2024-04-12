package com.example.areader.screens.search
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.areader.components.InputField
import com.example.areader.components.ReaderAppBar
import com.example.areader.model.Item
import com.example.areader.navigation.ReaderScreens


@Composable
fun ReaderBookSearchScreen(navController: NavController,
                           viewModel: BooksSearchViewModel = hiltViewModel(

                           )
){
    Scaffold(topBar = {
        ReaderAppBar(title = "Search Books",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            navController = navController,
            showProfile = false){
           // navController.popBackStack()
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }, content = {
            innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            ) {
            Surface {
                Column {
                    SearchForm(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        viewModel = viewModel
                    ){query ->
                        viewModel.searchBooks(query)

                    }
                    Spacer(modifier = Modifier.height(13.dp))
                    BookList(navController, viewModel)
                }

            }

        }
    })

}

@Composable
fun BookList(navController: NavController,viewModel: BooksSearchViewModel = hiltViewModel()) {
            val listOfBooks = viewModel.list
            if (viewModel.isLoading){
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    LinearProgressIndicator()
                    Text(text = "Loading...")
                }

            } else{
                LazyColumn(modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ){
                    items(items = listOfBooks){book ->
                        BookRow(book, navController)

                    }
                }

            }


}

@Composable
fun BookRow(
    book: Item,
    navController: NavController) {
    Card(
        modifier = Modifier
            .clickable {
                navController.navigate(ReaderScreens.DetailsScreen.name+ "/${book.id}")
            }
            .fillMaxWidth()
            .height(100.dp)
            .padding(3.dp),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(
            7.dp
        ),
    ) {
        Row(modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top) {
            val imageUrl : String = if (book.volumeInfo.imageLinks.smallThumbnail.isEmpty())
                "http://books.google.com/books/content?id=EzgzEAAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api"
            else {
                book.volumeInfo.imageLinks.smallThumbnail
            }
            Image(painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp))
            Column(modifier = Modifier.fillMaxHeight()) {
              Text(text = book.volumeInfo.title,
                  overflow = TextOverflow.Ellipsis)
                Text(text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)

                Text(text = "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)

                Text(text = " ${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = MaterialTheme.typography.labelSmall)



            }

        }


    }

}

@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: BooksSearchViewModel,
    Loading: Boolean = false,
    hint: String = "Search",
    onSearch: (String) -> Unit = {}

){
    Column {
        val searchQueryState = rememberSaveable {
            mutableStateOf("")}
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQueryState, labelId = "Search", enabled = true,
            onAction = KeyboardActions{
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )



    }

}

















