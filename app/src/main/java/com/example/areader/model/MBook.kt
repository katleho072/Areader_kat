package com.example.areader.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.sql.Timestamp

data class MBook(
  @Exclude var id: String? = null,
    var title: String? = null,
    var authors: String? = null,
    var notes: String? = null,
  @get:PropertyName(value = "book_photo_url")
  @set:PropertyName(value = "book_photo_url")
     var photoUrl: String? = null,
    var categories: String? = null,
    @get:PropertyName(value = " publishedDate")
    @set:PropertyName(value = " publishedDate")
    var publishedDate: String? = null,
    var rating: Double? = null,
    @get:PropertyName(value = "description")
    @set:PropertyName(value = "description")
    var description: String? = null,
    var pageCount: String? = null,
  @get:PropertyName(value = " startedReading")
  @set:PropertyName(value = " startedReading")
    var startedReading: Timestamp? = null,
  @get:PropertyName(value = " finished_reading_at")
  @set:PropertyName(value = " finished_reading_at")
    var finishedReading: Timestamp? = null,
    @get:PropertyName(value = "user_id")
    @set:PropertyName(value = "user_id")
    var userId: String? = null,
    @get:PropertyName(value = "google_book_id")
    @set:PropertyName(value = "google_book_id")
    var googleBookId: String? = null
)
