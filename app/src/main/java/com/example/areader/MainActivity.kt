package com.example.areader

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.areader.navigation.ReaderNavigation
import com.example.areader.ui.theme.AReaderTheme
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                 AReaderTheme {
                     ReaderApp()
                 }
            }
        }
    }


@Composable
fun ReaderApp(){

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 46.dp),
            color = MaterialTheme.colorScheme.background
        ) {
         Column(verticalArrangement = Arrangement.Center,
             horizontalAlignment = Alignment.CenterHorizontally) {
             ReaderNavigation()
        }
    }

}