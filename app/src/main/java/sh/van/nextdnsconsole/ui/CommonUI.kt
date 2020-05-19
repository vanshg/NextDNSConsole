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
                Text(text = stringResource(subtitle), style = MaterialTheme.typography.subtitle2)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            children()
        }
    }
}

@Composable
fun Field(@StringRes name: Int, value: List<String>?) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        TextForName(stringResource(id = name))
        Column { value?.forEach { TextForValue(text = it) } }
    }
}

@Composable
fun Field(@StringRes name: Int, value: String?) {
    Row(
        modifier = Modifier.fillMaxWidth() + Modifier.padding(top = 6.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextForName(stringResource(id = name))
        TextForValue(text = value.orEmpty())
    }
}

@Composable
fun TextForName(text: String) = Text(text = text, style = MaterialTheme.typography.subtitle2)

@Composable
fun TextForValue(text: String) = Text(text = text, style = MaterialTheme.typography.body1)

@Preview
@Composable
fun LoadingIndicatorCentered() {
    Box(modifier = Modifier.fillMaxSize(), gravity = ContentGravity.Center) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally))
    }
}