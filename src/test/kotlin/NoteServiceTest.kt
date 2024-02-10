import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import ru.netology.Comments
import ru.netology.NoteService
import ru.netology.Notes

class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        NoteService.clearNotes()
        NoteService.clearComments()
    }

    @Test
    fun addNote() {
        val result = NoteService.addNote(
            Notes(
                0,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
        val expected = 1
        assertEquals(result, expected)
    }

    @Test
    fun notesCreateComment() {
        NoteService.addNote(
            Notes(
                0,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )

        val result = NoteService.notesCreateComment(
            1,
            Comments(0, 0, 1111, "Комментарий №1 к посту 1")
        )
        val expected = 0
        assertEquals(result, expected)
    }

    @Test
    fun deleteNote() {
        NoteService.addNote(
            Notes(
                0,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
        NoteService.addNote(
            Notes(
                0,
                "Заметка #2",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )

        val result = NoteService.deleteNote(1)
        val expected = 1
        assertEquals(result, expected)
    }


    @Test
    fun notesDeleteComment() {
        NoteService.addNote(
            Notes(
                0,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
        NoteService.notesCreateComment(
            1,
            Comments(0, 0, 1111, "Комментарий №1 к посту 1")
        )
        NoteService.notesCreateComment(
            1,
            Comments(0, 0, 3333, "Комментарий №1 к посту 2")
        )

        val result = NoteService.notesDeleteComment(1)
        val expected = 1
        assertEquals(result, expected)
    }

    @Test
    fun editNote() {
        NoteService.addNote(
            Notes(
                0,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 111111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
        val result = NoteService.editNote(1, "NEW TITLE", "NEW TEXT", 1, 1, "4654", "123")
        val expected = 1
        assertEquals(result, expected)
    }


}