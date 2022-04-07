package dev.chintansoni.expensetracker.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

private val chartImage: ImageVector = Icons.Default.PieChartOutline
val ChartIcon: @Composable () -> Unit =
    { Icon(imageVector = chartImage, contentDescription = "Chart Image") }

private val personImage: ImageVector = Icons.Default.Person
val PersonIcon: @Composable () -> Unit =
    { Icon(imageVector = personImage, contentDescription = "Person Image") }

private val listImage: ImageVector = Icons.Default.List
val ListIcon: @Composable () -> Unit =
    { Icon(imageVector = listImage, contentDescription = "List Image") }

private val addImage: ImageVector = Icons.Default.Add
val AddIcon: @Composable () -> Unit =
    { Icon(imageVector = addImage, contentDescription = "Add Image") }

private val backImage: ImageVector = Icons.Default.ArrowBack
val BackIcon: @Composable () -> Unit =
    { Icon(imageVector = backImage, contentDescription = "Back Image") }

private val noteImage: ImageVector = Icons.Default.EventNote
val NoteIcon: @Composable () -> Unit =
    { Icon(imageVector = noteImage, contentDescription = "Note Image") }

private val dropDownImage: ImageVector = Icons.Default.ArrowDropDown
val DropDownIcon: @Composable () -> Unit =
    { Icon(imageVector = dropDownImage, contentDescription = "DropDown Image") }

private val eventImage: ImageVector = Icons.Default.Event
val EventIcon: @Composable () -> Unit =
    { Icon(imageVector = eventImage, contentDescription = "Event Image") }

private val categoryImage: ImageVector = Icons.Default.Category
val CategoryIcon: @Composable () -> Unit =
    { Icon(imageVector = categoryImage, contentDescription = "Category Image") }

private val doneImage: ImageVector = Icons.Default.Done
val DoneIcon: @Composable () -> Unit =
    { Icon(imageVector = doneImage, contentDescription = "Done Image") }

private val emailImage: ImageVector = Icons.Default.Mail
val EmailIcon: @Composable () -> Unit =
    { Icon(imageVector = emailImage, contentDescription = "Email Image") }

private val passwordImage: ImageVector = Icons.Default.Password
val PasswordIcon: @Composable () -> Unit =
    { Icon(imageVector = passwordImage, contentDescription = "Password Image") }

private val navigateNextImage: ImageVector = Icons.Default.NavigateNext
val NavigateNextIcon: @Composable () -> Unit =
    { Icon(imageVector = navigateNextImage, contentDescription = "Arrow Right Icon") }

private val settingImage: ImageVector = Icons.Default.Settings
val SettingIcon: @Composable () -> Unit =
    { Icon(imageVector = settingImage, contentDescription = "Setting Icon") }

private val deleteImage: ImageVector = Icons.Default.Delete
val DeleteIcon: @Composable () -> Unit =
    { Icon(imageVector = deleteImage, contentDescription = "Delete Icon") }

private val editImage: ImageVector = Icons.Default.Edit
val EditIcon: @Composable () -> Unit =
    { Icon(imageVector = editImage, contentDescription = "Edit Icon") }

@Composable
fun DrawableIcon(@DrawableRes resId: Int, contentDescription: String? = null) =
    Icon(painter = painterResource(id = resId), contentDescription = contentDescription)