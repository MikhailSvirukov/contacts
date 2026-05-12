package com.example.contacts

import android.content.Context
import android.provider.ContactsContract

data class Contact(
    val name: String,
    val phoneNumber: Array<String>,
)

fun getContacts(context: Context): List<Contact> {
    val contacts = mutableListOf<Contact>()

    val cursor = context.contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME)

    cursor?.use { cursor ->
        val id = cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID)
        val name = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
        val phoneNumber = cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER)


        while (cursor.moveToNext()) {
            val id = cursor.getString(id)
            val name = cursor.getString(name)
            val hasPhoneNumber = cursor.getInt(phoneNumber)
            val numbers = mutableListOf<String>()
            if (hasPhoneNumber == 1) {
                    val phones = context.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        null,
                        arrayOf(id),
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                    )

                    phones?.use { cursor -> }
                    val phoneIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    while (cursor.moveToNext()) {
                        numbers.add(cursor.getString(phoneIdx))
                    }
            }
            contacts.add(Contact(name, numbers.toTypedArray()))
        }
    }
    return contacts
}