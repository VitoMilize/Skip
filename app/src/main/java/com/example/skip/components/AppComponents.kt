package com.montanainc.simpleloginscreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.skip.ui.theme.AccentColor
import com.example.skip.ui.theme.BgColor
import com.example.skip.ui.theme.Primary
import com.example.skip.ui.theme.Secondary
import com.example.skip.ui.theme.TextColor

@Composable
fun NormalTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp),
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@Composable
fun HeadingTextComponent(value: String) {
    Text(
        text = value,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(),
        style = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Normal
        ),
        color = TextColor,
        textAlign = TextAlign.Center
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponent(labelValue: String, icon: ImageVector, textValue: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = textValue,
        onValueChange = { onTextChanged(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentColor,
            focusedLabelColor = AccentColor,
            cursorColor = Primary,
            containerColor = BgColor,
            focusedLeadingIconColor = AccentColor,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        keyboardOptions = KeyboardOptions.Default,
        textStyle = TextStyle(color = TextColor)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponentNoIcon(labelValue: String, textValue: String, onTextChanged: (String) -> Unit) {
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = textValue,
        onValueChange = { onTextChanged(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentColor,
            focusedLabelColor = AccentColor,
            cursorColor = Primary,
            containerColor = BgColor,
            focusedLeadingIconColor = AccentColor,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        keyboardOptions = KeyboardOptions.Default,
        textStyle = TextStyle(color = TextColor)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponentSelector(
    labelValue: String,
    selectedOption: String,
    options: List<String>,
    onOptionSelected: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { expanded = true }
            .padding(vertical = 4.dp)
    ) {
        OutlinedTextField(
            label = { Text(text = labelValue) },
            value = selectedOption,
            onValueChange = {},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AccentColor,
                focusedLabelColor = AccentColor,
                cursorColor = Primary,
                containerColor = BgColor,
                focusedLeadingIconColor = AccentColor,
                disabledTextColor = TextColor,
                disabledBorderColor = TextColor
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions.Default,
            textStyle = TextStyle(color = TextColor),
            readOnly = true,
            enabled = false
        )
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
    ) {
        options.forEach { option ->
            DropdownMenuItem(
                text = { Text(text = option) },
                onClick = {
                    onOptionSelected(option)
                    expanded = false
                }
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextFieldComponentNoIconClickable(
    labelValue: String,
    textValue: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { onClick() }
    ) {
        OutlinedTextField(
            label = { Text(text = labelValue) },
            value = textValue,
            onValueChange = {},
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = AccentColor,
                focusedLabelColor = AccentColor,
                cursorColor = Primary,
                containerColor = BgColor,
                focusedLeadingIconColor = AccentColor,
                disabledTextColor = TextColor,
                disabledBorderColor = TextColor
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            keyboardOptions = KeyboardOptions.Default,
            textStyle = TextStyle(color = TextColor),
            readOnly = true,
            enabled = false // Отключаем редактирование
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(labelValue: String, icon: ImageVector, textValue: String, onTextChanged: (String) -> Unit
) {
    var isPasswordVisible by remember {
        mutableStateOf(false)
    }
    OutlinedTextField(
        label = {
            Text(text = labelValue)
        },
        value = textValue,
        onValueChange = { onTextChanged(it) },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = AccentColor,
            focusedLabelColor = AccentColor,
            cursorColor = Primary,
            containerColor = BgColor,
            focusedLeadingIconColor = AccentColor,
        ),
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "profile"
            )
        },
        trailingIcon = {
            val iconImage =
                if (isPasswordVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            val description = if (isPasswordVisible) "Show Password" else "Hide Password"
            IconButton(onClick = {
                isPasswordVisible = !isPasswordVisible
            }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        textStyle = TextStyle(color = TextColor)
    )
}

@Composable
fun AccountQueryComponent(
    textQuery: String,
    textClickable: String,
    navController: NavHostController
) {
    val annonatedString = buildAnnotatedString {
        withStyle(style = SpanStyle(color = TextColor, fontSize = 15.sp)) {
            append(textQuery)
        }
        withStyle(style = SpanStyle(color = Secondary, fontSize = 15.sp)) {
            pushStringAnnotation(tag = textClickable, annotation = textClickable)
            append(textClickable)
        }
    }

    ClickableText(text = annonatedString, onClick = {
        annonatedString.getStringAnnotations(it, it)
            .firstOrNull()?.also { annonation ->
                if (annonation.item == "Войдите") {
                    navController.navigate("Login")
                } else if (annonation.item == "Зарегистрируйтесь") {
                    navController.navigate("Signup")
                }
            }
    })
}

@Composable
fun CreateRequestButton(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Создать заявку",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Создать заявку",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ViewOwnRequestsButton(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.List, // Иконка списка
                contentDescription = "Открыть мои заявки",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Открыть мои заявки",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ViewAllRequestsButton(onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(48.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
        ) {
            Icon(
                imageVector = Icons.Outlined.List, // Иконка списка
                contentDescription = "Открыть заявки студентов",
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Открыть заявки студентов",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
