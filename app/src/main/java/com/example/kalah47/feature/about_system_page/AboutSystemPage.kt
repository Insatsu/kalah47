package com.example.kalah47.feature.about_system_page

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
import com.example.kalah47.feature.game_rule_page.TOP_PADDING

@Composable
fun AboutSystemPage() {
//    Scaffold {
//        val c = it
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(vertical = 15.dp, horizontal = 20.dp),
////            horizontalAlignment = Alignment.CenterHorizontally,
//
//        ) {
//            Text(
//                text = stringResource(id = R.string.about_system),
//                style = MaterialTheme.typography.headlineLarge,
//                textAlign = TextAlign.Center,
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            Text(
//                text = "\tКалах — это древняя настольная игра, которая относится к категории игр с семенами и принадлежит к группе " +
//                        "\"мели\" (или \"калахи\") в Африке и на Ближнем Востоке. История калаха уходит корнями в тысячи лет назад," +
//                        " и игра, как предполагается, возникла в Африке, вероятно, в регионе, который сегодня включает современные " +
//                        "страны, такие как Эфиопия и Судан",
//                style = MaterialTheme.typography.bodyMedium,
//                textAlign = TextAlign.Start,
//                modifier = Modifier.padding(top = 20.dp),
//                lineHeight = TextUnit(25f, TextUnitType.Sp),
//                fontSize = TextUnit(20f, TextUnitType.Sp)
//            )
//
//        }
//
//    }

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
                        text = stringResource(id = R.string.about_system),
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
                    title = "Об игре",
                    index = 1,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Игровой интерфейс",
                    index = 2,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Игровой процесс",
                    index = 3,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Настройки",
                    index = 4,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )
                ContentButton(
                    title = "Онлайн игра",
                    index = 5,
                    lazyListState = lazyColumnState,
                    coroutineScope = coroutine
                )

                Divider(
                    Modifier.padding(top = TOP_PADDING),
                    thickness = 2.dp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

//          * 1-5 Items. Contents
            item {
                ContentText(
                    title = "Об игре",
                    content = "Калах — это древняя настольная игра, которая относится к категории игр с семенами и принадлежит к группе " +
                            "\"мели\" (или \"калахи\") в Африке и на Ближнем Востоке. История калаха уходит корнями в тысячи лет назад," +
                            " и игра, как предполагается, возникла в Африке, вероятно, в регионе, который сегодня включает современные " +
                            "страны, такие как Эфиопия и Судан" +
                            "\nПодробнее ознакомиться с правилами игры можно в соответствующем разделе на главном экране.",                )
            }
            item {
                ContentText(
                    title = "Игровой интерфейс",
                    content = "После ознакомления с правилами игры можно приступить к самому игровому процессу. Для этого необходимо определиться" +
                            "с типом игры. На выбор имеется 3 варианта:" +
                            "\n  - Одиночная игра - игра с противником на одном устройстве" +
                            "\n  - Одиночная игра с ботом - игра с искусственным интелектом, сложность которого можно установить в разделе настроек. Также" +
                            "там можно определить, кто будет ходить первым в этом режиме игры" +
                            "\n  - Онлайн игра - игра с противником по сети" +
                            "\nВыбор осуществляется также на главном экране. После выбора и начала игры будет отображено игровое поле, состоящее из лунок, калахов и камушков." +
                            "\nВ каждой лунке, как и в калахе, показывается её текущее количество камушков как визуальными кружками, так и в числовом формате." +
                            "\nВ центре экрана располагается информация об игроке, чей ход в данный момент ожидается. В случае попытки сходить не в свой ход появится всплывающее сообщение с предупреждением." +
                            "\nЛунки и калах для каждого игрока выделены разными цветами для упрощения их определения. К тому же для текущего игрока их цвет будет ярче, а около" +
                            "его калаха будет звёздочка." +
                            "\nПо бокам экрана находятся стрелки, указывающие в каком направлении идёт игра." +
                            "\nВ случае завершения игры появится окно с её результатом. Подобное окно будет также в онлайн игре для отображения информации о статусе игры." +
                            "\nВ игре с ботом и онлайн игре рядом с калахом игрока/бота находиться его аватарка и никнейм"
                )
            }
            item {
                ContentText(
                    title = "Игровой процесс",
                    content = "Игра начинается с отображения игрового поля. Для такого, чтобы осуществить ход необходимо нажать на лунку, из которой должны распределиться камешки согласно правилам." +
                            "\nНажимать на калах смысла не имеет."
                )
            }
            item {
                ContentText(
                    title = "Настройки",
                    content = "На экране настроек можно установить тему оформления игрового поля из предложенных трёх вариантов (доска, метал, кирпич), количество лунок с каждой стороны (4-8)," +
                            "количество камушков в каждой лунке (3-12) и настройки бота. В последние входит определение первого ходящего и уровень сложности бота (1-5)"
                )
            }
            item {
                ContentText(
                    title = "Онлайн игра",
                    content = "Экран онлайн игры содержит настройки профиля игрока. Можно выбрать аватар из 10 представленных, нажав на большой круг с изображением" +
                            "и на нужную картинку в нижнем меню. " +
                            "\nПосле него идёт настройка никнейма, который может содержать от 4 до 10 символов кириллицы, латиницы, цифр и нижнего подчеркивания." +
                            "В случае несоответствия требованиям появится сообщение об ошибке и сохранить никнейм будет нельзя. Если всё правильно, необходимо нажать на " +
                            "галочку после поля ввода либо на кнопку \"подтвердить\" на клавиатуре." +
                            "\nКнопка \"Создать сессию\" создаёт онлайн сессию, открывает игровое поле с показывает информацию о своём идентификаторе." +
                            "\nСам идентификатор необходимо вводить в соответствующем поле на экране онлайн игры. Если сессия с таким идентификатором существует, произойдёт" +
                            "успешной вход, иначе вывод сообщения об ошибке. Также вводить можно только цифры и его длина должна быть равна 5."
                )
            }
        }
    }
}





@Composable
@Preview
internal fun Test() {
    AboutSystemPage()
}