package com.example.muplayer

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.muplayer.model.PlayerData
import com.example.muplayer.ui.theme.MUPlayerTheme

@Composable
fun PlayerApp(
    modifier: Modifier = Modifier,
    navController: NavController,
    onItemClick: (String) -> Unit // Add onItemClick as a parameter
) {
    Box(modifier = modifier) {
        LazyColumn {
            item {
                AboutButton(onClick = {
                    navController.navigate(Screen.About.createRoute())
                })
            }
            items(PlayerData.player, key = { it.id }) { player ->
                PlayerListItem(
                    name = player.name,
                    description = player.description,
                    photoUrl = player.photoUrl,
                    modifier = Modifier.fillMaxWidth(),
                    onItemClick = { // Invoke onItemClick with the player ID
                        onItemClick(player.id)
                    }
                )
            }
        }
    }
}


@Composable
fun PlayerListItem(
    name: String,
    description: String,
    photoUrl: String,
    modifier: Modifier = Modifier,
    onItemClick: () -> Unit
) {
    val maxCharacterLimit = 100 // Set your desired maximum character limit here

    Row(
        modifier = Modifier
            .padding(8.dp)
            .clickable(onClick = { onItemClick }),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
            )
            val displayedDescription = if (description.length > maxCharacterLimit) {
                "${description.substring(0, maxCharacterLimit)}..."
            } else {
                description
            }
            Text(text = displayedDescription)
        }
    }
}

@Composable
fun PlayerDetailScreen(playerId: String) {
    val player = PlayerData.player.find { it.id == playerId }

    if (player != null) {
        // Display player details
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = player.photoUrl,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = player.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = player.description,
                style = MaterialTheme.typography.body1
            )
        }
    } else {
        // Player not found
        Text(text = "Player not found")
    }
}

@Composable
fun AboutScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = com.example.muplayer.R.drawable.profile),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(200.dp)
                    .clip(shape = MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Figo Akmal Munir",
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "figoakmal542@gmail.com",
                style = MaterialTheme.typography.body1
            )
        }
    }
}

@Composable
fun AboutButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Text(text = "About")
    }
}


@Preview(showBackground = true)
@Composable
fun PlayerAppPreview() {
    val mockNavController = rememberNavController()
    MUPlayerTheme() {
        PlayerApp(
            navController = mockNavController,
            onItemClick = { playerId ->
                // Handle the item click event here
                mockNavController.navigate(Screen.Detail.createRoute(playerId))
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PlayerDetailScreenPreview() {
    val player = PlayerData.player.first() // Assuming there is at least one player in the list
    MUPlayerTheme() {
        PlayerDetailScreen(playerId = player.id)
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListItemPreview() {
    MUPlayerTheme() {
        PlayerListItem(
            name = "David De Gea",
            description = "",
            photoUrl = "",
            onItemClick = {} // Provide a dummy lambda function for onItemClick
        )
    }
}
