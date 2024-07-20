package com.katesune.filmograf.presentation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.katesune.filmograf.R
import com.katesune.filmograf.ui.theme.Typography

private val tinyPadding = 8.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieSearchMenu(
    confirmValue: (String) -> Unit,
    searchQuery: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val query = remember { mutableStateOf(searchQuery) }

    TopAppBar(
        title = {
            TextField(
                value = query.value,

                onValueChange = { value ->
                    query.value = value },

                placeholder = {
                    Text(text = stringResource(id = R.string.initial_search))
                },

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.DarkGray.copy(alpha = 0f),
                    unfocusedBorderColor = Color.White,
                ),

                textStyle = Typography.bodyLarge,

                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        checkSearchValue(confirmValue, query)
                        keyboardController?.hide()
                    }),
                )},
        navigationIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = stringResource(id = R.string.search_icon_description),
                modifier = Modifier
                    .padding(tinyPadding)
                    .clickable {
                        if (query.value.isNotBlank()) {
                            confirmValue(query.value)
                        }
                    }
            )
        },
        actions = {
            if (query.value.isNotBlank()) {
                IconButton(
                    onClick = {
                        query.value = ""
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(tinyPadding)
                    )
                }
            }
        }
    )
}


private fun checkSearchValue(
    confirmValue: (String) -> Unit,
    searchQuery: MutableState<String>
) {
    if (searchQuery.value.isNotBlank()) {
        confirmValue(searchQuery.value)
    }
}

@Preview(showBackground = true)
@Composable
fun SearchMenuPreview() {
    val text = "Search movie by title"
    MovieSearchMenu(
        {},
        text
    )
}