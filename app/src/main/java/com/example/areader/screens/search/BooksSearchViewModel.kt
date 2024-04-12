package com.example.areader.screens.search

import android.content.res.Resources
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.areader.data.DataOrException
import com.example.areader.model.Item
import com.example.areader.repository.BooksRepository
import com.google.rpc.context.AttributeContext.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.grpc.internal.SharedResourceHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import javax.inject.Inject


@HiltViewModel
class BooksSearchViewModel @Inject constructor(private val repository: BooksRepository):
    ViewModel() {
        var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)
    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch (Dispatchers.Default){
            if (query.isEmpty()){
                return@launch
            }
            try {
                when(val response = repository.getBooks(query)){
                    is com.example.areader.data.Resource.Success -> {
                        list = response.data!!
                        if (list.isNotEmpty()) isLoading = false

                    }
                 is com.example.areader.data.Resource.Error ->{
                     isLoading = false
                 }

                    else -> {isLoading = false}
                }

            }catch (exception: Exception){
                isLoading = false

            }
        }

    }


}


//    val listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>>
//    = mutableStateOf(DataOrException(null, true, Exception("")))
//
//    init {
//        searchBooks("android")
//    }
//
//  fun searchBooks(query: String) {
//        viewModelScope.launch {
//            if (query.isEmpty()){
//                return@launch
//            }
//            listOfBooks.value.loading = true
//            listOfBooks.value = repository.getBooks(query)
//            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false
//
//        }
//
//    }
//
//}