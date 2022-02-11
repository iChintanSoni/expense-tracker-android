package dev.chintansoni.expensetracker.ui.theme

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChartOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

private val ChartImage: ImageVector = Icons.Default.PieChartOutline
val ChartIcon: @Composable () -> Unit =
    { Icon(imageVector = ChartImage, contentDescription = "Chart Image") }

private val PersonImage: ImageVector = Icons.Default.Person
val PersonIcon: @Composable () -> Unit =
    { Icon(imageVector = PersonImage, contentDescription = "Person Image") }

private val ListImage: ImageVector = Icons.Default.List
val ListIcon: @Composable () -> Unit =
    { Icon(imageVector = ListImage, contentDescription = "List Image") }

private val AddImage: ImageVector = Icons.Default.Add
val AddIcon: @Composable () -> Unit =
    { Icon(imageVector = AddImage, contentDescription = "Add Image") }

private val BackImage: ImageVector = Icons.Default.ArrowBack
val BackIcon: @Composable () -> Unit =
    { Icon(imageVector = BackImage, contentDescription = "Back Image") }

private val NoteImage: ImageVector = Icons.Default.EventNote
val NoteIcon: @Composable () -> Unit =
    { Icon(imageVector = NoteImage, contentDescription = "Note Image") }

private val DropDownImage: ImageVector = Icons.Default.ArrowDropDown
val DropDownIcon: @Composable () -> Unit =
    { Icon(imageVector = DropDownImage, contentDescription = "DropDown Image") }

private val EventImage: ImageVector = Icons.Default.Event
val EventIcon: @Composable () -> Unit =
    { Icon(imageVector = EventImage, contentDescription = "Event Image") }

val CategoryImage: ImageVector = Icons.Default.Category
val categoryIcon: @Composable () -> Unit =
    { Icon(imageVector = CategoryImage, contentDescription = "Category Image") }

val DoneImage: ImageVector = Icons.Default.Done
val doneIcon: @Composable () -> Unit =
    { Icon(imageVector = DoneImage, contentDescription = "Done Image") }

val EmailImage: ImageVector = Icons.Default.Mail
val emailIcon: @Composable () -> Unit =
    { Icon(imageVector = EmailImage, contentDescription = "Email Image") }

val PasswordImage: ImageVector = Icons.Default.Password
val passwordIcon: @Composable () -> Unit =
    { Icon(imageVector = PasswordImage, contentDescription = "Password Image") }