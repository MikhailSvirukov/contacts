package com.example.contacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                App()
            }
        }
    }
}

@Composable
fun ContactCard(contact: Contact, modifier: Modifier = Modifier) {
    Card(modifier) {
        Column {
            Text(contact.name, style = MaterialTheme.typography.bodyLarge)
            contact.phoneNumber.forEach { number ->
                Text(modifier = modifier.padding(0.dp, 5.dp), text = number)
            }
        }
    }
}

@Composable
fun CardList(cardList: List<Contact>, modifier: Modifier = Modifier) {
    LazyColumn(modifier) {
        items(cardList) { contact ->
            ContactCard(contact = contact)
        }
    }
}

@Composable
fun App() {
    val context = LocalContext.current
    val contacts = remember(context) {
        getContacts(context)
    }
    CardList(cardList = contacts)
}
