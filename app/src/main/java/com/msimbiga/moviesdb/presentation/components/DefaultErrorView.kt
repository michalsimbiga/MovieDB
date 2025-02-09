package com.msimbiga.moviesdb.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.msimbiga.moviesdb.R

@Composable
fun DefaultErrorView(onRetryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(stringResource(R.string.failed_to_load_data_text))
        Button(onClick = onRetryClick) {
            Text(stringResource(R.string.failed_to_load_data_retry_text))
        }
    }
}