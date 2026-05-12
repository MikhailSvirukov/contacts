package com.example.contacts

import android.Manifest
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private fun requestCallPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_CONTACTS, Manifest.permission.CALL_PHONE),
            100
        )
    }

    private fun makeCall(number: String) {
        startActivity(
            Intent(Intent.ACTION_CALL).apply {
                data = Uri.parse("tel:$number")
            }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCallPermissions()
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                App(onNumberClick = ::makeCall)
            }
        }
    }

class AppViewModel(application: Application) : AndroidViewModel(application) {
    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.value = getContacts(getApplication<Application>().contentResolver)
        }
    }
}

@Composable
fun ContactCard(
    contact: Contact,
    onNumberClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = contact.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )

            if (contact.phoneNumber.isNotEmpty()) {
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Text(
                    text = "Телефоны",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                contact.phoneNumber.forEach { number ->
                    Text(
                        text = number,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .clickable { onNumberClick(number) },
                    )
                }
            }
        }
    }
}

@Composable
fun CardList(
    cardList: List<Contact>,
    onNumberClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Column(modifier = Modifier.padding(bottom = 4.dp)) {
                Text(
                    text = "Контакты",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                Text(
                    text = "Список контактов с телефона",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
        items(cardList) { contact ->
            ContactCard(contact = contact, onNumberClick = onNumberClick)
        }
    }
}

@Composable
fun App(viewModel: AppViewModel = viewModel(), onNumberClick: (String) -> Unit) {
    val contacts by viewModel.contacts.collectAsState()
    CardList(
        cardList = contacts,
        onNumberClick = onNumberClick
    ) }
}
