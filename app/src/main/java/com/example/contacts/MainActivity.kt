package com.example.contacts

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS),
            1
        )
        setContent {
            CardList()
        }
    }
}

@Composable
fun ContactCard(contact: Contact, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column {
            Text(contact.name, style = MaterialTheme.typography.bodyLarge)
            LazyColumn {
                items(contact.phoneNumber.size) { number ->
                    Text(text = number.toString())
                }
            }
        }
    }
}

@Composable
fun CardList(modifier: Modifier = Modifier) {
    val contacts = getContacts(LocalContext.current)
    LazyColumn(modifier) {
        items(contacts.size) { contact ->
            ContactCard(contact = contacts[contact])
        }
    }
}
