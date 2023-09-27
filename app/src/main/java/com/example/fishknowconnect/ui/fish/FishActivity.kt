package com.example.fishknowconnect.ui.fish

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fishknowconnect.DisplayList
import com.example.fishknowconnect.R
import com.example.fishknowconnect.ui.IndeterminateCircularIndicator
import com.example.fishknowconnect.ui.ToolBarLayout
import com.example.fishknowconnect.ui.fish.ui.theme.FishKnowConnectTheme
import com.example.fishknowconnect.ui.newPost.NewPostActivity

class FishActivity : ComponentActivity() {
    val viewModel: FishViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FishKnowConnectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        ToolBarLayout(this@FishActivity.getString(R.string.textview_fish))
                        FishScreen(this@FishActivity.getString(R.string.textview_fish), viewModel)
                        val context = LocalContext.current as? Activity
                        when (val responseValue = viewModel.state.collectAsState().value) {
                            FishState.Loading -> IndeterminateCircularIndicator()
                            is FishState.Success -> responseValue.response?.let {
                                DisplayList(it, context) }
                            is FishState.Error -> ShowErrorMessage()
                            else -> {
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * shows error dialog
 */
@Composable
fun ShowErrorMessage() {
    val context = LocalContext.current as? Activity
    Toast.makeText(context, stringResource(id = R.string.text_something_went_wrong), Toast.LENGTH_SHORT).show()
}


/**
 * Fish screen view
 */
@Composable
fun FishScreen(name: String, viewModel: FishViewModel) {
    LaunchedEffect(Unit, block = {
        viewModel.getAllContents()
    })
    val activity = (LocalContext.current as? Activity)
    //create new post button
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 16.dp, vertical = 0.dp)),
        horizontalAlignment = Alignment.End,
    ) {
        Button(modifier = Modifier.padding(vertical = 25.dp), onClick = {
            //start new post screen
            val intent = Intent(activity, NewPostActivity::class.java).apply {
                putExtra("type", name)
            }
            activity?.startActivity(intent)
        }) {
            Text(
                text = stringResource(id = R.string.button_new_post),
                textAlign = TextAlign.Right,
                style = TextStyle(
                    fontSize = 20.sp, color = Color.White, fontFamily = FontFamily.SansSerif
                )
            )
            Icon(
                imageVector = Icons.Default.AddCircle,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 5.dp)
            )
        }
    }
    //title
    Text(
        text = "LATEST POSTS...", style = TextStyle(
            fontSize = 20.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
    )
}


@Preview(showBackground = true)
@Composable
fun FishScreenPreview() {
    FishKnowConnectTheme {
        val viewModel = FishViewModel()
        FishScreen("Fishscreen", viewModel)
    }
}