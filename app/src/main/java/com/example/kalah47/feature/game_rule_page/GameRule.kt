package com.example.kalah47.feature.game_rule_page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kalah47.R
import com.example.kalah47.feature.common_components.ContentButton
import com.example.kalah47.feature.common_components.ContentText
import com.example.kalah47.feature.common_components.ScrollToTopButton


val TOP_PADDING = 20.dp

@Composable
fun GameRule() {
    val lazyColumnState = rememberLazyListState()
    val coroutine = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
//          * Floating button to scroll to top
            ScrollToTopButton(lazyColumnState, coroutine)
        },
    ) {
        val c = it

        LazyColumn(
            state = lazyColumnState,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 25.dp, horizontal = 20.dp),
        ) {
//          * 0 Item. Title and content
            item {
                Box(Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.game_rules),
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Text(
                    text = "Содержание:",
                    fontSize = TextUnit(20f, TextUnitType.Sp),
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                ContentButton(
                    title = "Обозначение лунок",
                    index = 1,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Исходная позиция",
                    index = 2,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Посев",
                    index = 3,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Захват камней",
                    index = 4,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Повтор хода",
                    index = 5,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Окончание партии",
                    index = 6,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )

                Divider(
                    Modifier.padding(top = TOP_PADDING),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

//          * 1-6 Items. Contents
            item {
                ContentText(
                    title = "Обозначение лунок",
                    content = "Лунки обозначаются по порядку буквами или цифрами слева-направо со стороны каждого игрока."
                )
            }
            item {
                ContentText(
                    title = "Исходная позиция",
                    content = "Перед началом игры во все 12 лунок (кроме больших лунок «калахов») " + "раскладывают одинаковое количество камней от 3 до 6 штук в лунку. " + "Каждому игроку принадлежит ближний к нему ряд из 6 лунок и одна большая лунка «калах», расположенная справа от него."
                )
            }
            item {
                ContentText(
                    title = "Посев",
                    content = "Посев (ходы) делают по очереди, кроме случаев повтора хода. " + "Право первого хода определяют жребием или по договорённости. Во время своего хода игрок выбирает " + "одну любую из своих 6 лунок, вынимает все находящиеся в ней камни и раскладывает их против часовой " + "стрелки по одному камню во все последующие лунки, в том числе в свой «калах», но пропуская «калах» " + "противника. Направление движения: в своём ряду слева направо до своего «калаха» далее в ряду противника " + "справа налево, минуя его «калах» и возвращаясь в свой ряд. Если делается ход из лунки, содержащей 13 и " + "более камней, то после кругового обхода доски в неё тоже кладётся один камень. Ход завершается при " + "отсутствии условия повтора хода и передаётся сопернику."
                )
            }
            item {
                ContentText(
                    title = "Захват камней",
                    content = "Как уже описано выше, захват одного камня осуществляется простым ходом до своего «калаха» (и далее), "
                            + "где камень остаётся захваченным. Более многочисленный захват происходит в том случае, "
                            + "если последний в ходе камень игрок кладёт в пустую лунку, а противоположная "
                            + "лунка не пуста, тогда он забирает все камни из противоположной лунки вместе "
                            + "со своим последним камнем и переносит в свой «калах». Из «калаха» камни не вынимаются до окончания партии."
                )
            }
            item {
                ContentText(
                    title = "Повтор хода",
                    content = "Если последний в ходе камень попадает в «калах», то игрок делает ещё один ход. " + "Так ход может повторяться несколько раз."
                )
            }
            item {
                ContentText(
                    title = "Окончание партии",
                    content = "Игра завершается, когда один из игроков захватывает больше половины всех камней. " + "Либо как только все лунки одного из игроков полностью опустеют, в этом случае оставшиеся " + "камни захватывает тот игрок, которому они принадлежат. Набравший большее количество камней становится победителем."
                )
            }
        }
    }
}





@Composable
@Preview
internal fun Test() {
    GameRule()
}