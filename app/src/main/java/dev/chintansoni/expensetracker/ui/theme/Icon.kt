package dev.chintansoni.expensetracker.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChartOutline
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

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
val CategoryIcon: @Composable () -> Unit =
    { Icon(imageVector = CategoryImage, contentDescription = "Category Image") }

val DoneImage: ImageVector = Icons.Default.Done
val DoneIcon: @Composable () -> Unit =
    { Icon(imageVector = DoneImage, contentDescription = "Done Image") }

val EmailImage: ImageVector = Icons.Default.Mail
val emailIcon: @Composable () -> Unit =
    { Icon(imageVector = EmailImage, contentDescription = "Email Image") }

val PasswordImage: ImageVector = Icons.Default.Password
val passwordIcon: @Composable () -> Unit =
    { Icon(imageVector = PasswordImage, contentDescription = "Password Image") }

val navigateNextImage: ImageVector = Icons.Default.NavigateNext
val NavigateNextIcon: @Composable () -> Unit =
    { Icon(imageVector = navigateNextImage, contentDescription = "Arrow Right Icon") }

val settingImage: ImageVector = Icons.Default.Settings
val SettingIcon: @Composable () -> Unit =
    { Icon(imageVector = settingImage, contentDescription = "Setting Icon") }

val deleteImage: ImageVector = Icons.Default.Delete
val DeleteIcon: @Composable () -> Unit =
    { Icon(imageVector = deleteImage, contentDescription = "Delete Icon") }

@Composable
fun DrawableIcon(@DrawableRes resId: Int, contentDescription: String? = null) {
    Icon(painter = painterResource(id = resId), contentDescription = contentDescription)
}