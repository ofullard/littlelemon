package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DividerDefaults.color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.theme.PrimaryGreen
import com.example.littlelemon.ui.theme.PrimaryYellow
import org.slf4j.MDC.put

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Onboarding(navController: NavController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.littlelemonlogo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Let's get to know you",
            style = MaterialTheme.typography.headlineSmall,
            color = PrimaryGreen
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(
                    "First Name",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = { Text("Last Name", style = MaterialTheme.typography.bodyMedium) }
        )
        Spacer(modifier = Modifier.height(8.dp))

        var isValidEmail by rememberSaveable { mutableStateOf(true) }
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isValidEmail = isValidEmail(it)
            },
            label = { Text("Email Address", style = MaterialTheme.typography.bodyMedium) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = !isValidEmail,
            supportingText = {
                if (!isValidEmail) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Invalid email",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            },
            trailingIcon = {
                if (!isValidEmail)
                    Icon(Icons.Filled.Error, "error", tint = MaterialTheme.colorScheme.error)
            },

            )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()) {
                Toast.makeText(
                    context,
                    "Registration unsuccessful. Please enter all data.",
                    Toast.LENGTH_SHORT
                ).show()


                sharedPreferences.edit(commit = true) { putString(FIRST_NAME, firstName) }
                sharedPreferences.edit(commit = true) { putString(LAST_NAME, lastName) }
                sharedPreferences.edit(commit = true) { putString(EMAIL, email) }

                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(HomeDestination.route)

            } else {
                Toast.makeText(
                    context,
                    "Invalid Details, Please try again",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }

        })

        {
            Text(
                "Register", style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFF4CE14)
            )
        }


    }

}
fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    val navController = rememberNavController()
    Onboarding(navController)
}
