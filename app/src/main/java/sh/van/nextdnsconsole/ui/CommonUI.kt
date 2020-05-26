package sh.van.nextdnsconsole.ui

import androidx.annotation.StringRes
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.ContentGravity
import androidx.ui.foundation.Text
import androidx.ui.foundation.VerticalScroller
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.CircularProgressIndicator
import androidx.ui.material.MaterialTheme
import androidx.ui.material.Switch
import androidx.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.dp

@Composable
fun Screen(content: @Composable() () -> Unit) = MaterialTheme {
    VerticalScroller { Column(modifier = Modifier.fillMaxWidth()) { content() } }
}

@Composable
fun Section(
    @StringRes title: Int,
    @StringRes subtitle: Int? = null,
    @StringRes subtitle2: Int? = null,
    children: @Composable ColumnScope.() -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth() + Modifier.padding(8.dp), elevation = 4.dp) {
        Column(modifier = Modifier.fillMaxWidth() + Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = stringResource(subtitle), style = MaterialTheme.typography.subtitle1)
            }
            if (subtitle2 != null) {
                Spacer(modifier = Modifier.padding(2.dp))
                Text(text = stringResource(subtitle2), style = MaterialTheme.typography.subtitle2)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            children()
        }
    }
}

@Composable
fun TextItem(@StringRes name: Int, value: List<String>?) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NameText(stringResource(id = name))
        Column { value?.forEach { ValueText(text = it) } }
    }
}

@Composable
fun TextItem(@StringRes name: Int, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NameText(stringResource(id = name))
        ValueText(text = value.orEmpty())
    }
}

@Composable
fun ToggleItem(@StringRes name: Int, value: Boolean, onCheckedChange: (Boolean) -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NameText(stringResource(id = name))
        Switch(checked = value, onCheckedChange = onCheckedChange)
    }
}

@Composable
fun SingleItemToggleSection(
    @StringRes title: Int,
    @StringRes subtitle: Int? = null,
    @StringRes name: Int,
    value: Boolean,
    @StringRes subtitle2: Int? = null,
    onCheckedChange: (Boolean) -> Unit = {}
) = Section(title, subtitle, subtitle2) { ToggleItem(name, value, onCheckedChange) }

@Composable
fun NameText(text: String) = Text(text = text, style = MaterialTheme.typography.subtitle2)

@Composable
fun ValueText(text: String) = Text(text = text, style = MaterialTheme.typography.body1)

@Preview
@Composable
fun LoadingIndicatorCentered() {
    Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}