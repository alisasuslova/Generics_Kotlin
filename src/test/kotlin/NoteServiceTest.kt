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

        val result = NoteService.notesDeleteComment(1, 1)
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

    @Test
    fun notesEditComment() {
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

        val result = NoteService.notesEditComment(1, 0, "Edit Comment")
        val expected = 1
        assertEquals(result, expected)
    }

    /*@Test
    fun getNotes() {
        NoteService.addNote(
            Notes(
                1,
                "Заметка #1",
                "текст заметки",
                210124,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_1",
                privacy = 2,
                comment_privacy = 2,
                user_id = 1111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )

        NoteService.addNote(
            Notes(
                2,
                "Заметка #2",
                "текст заметки",
                100224,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_100",
                privacy = 2,
                comment_privacy = 2,
                user_id = 20002,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )

        val expected : List<Notes> = [Notes(note_id=1, title="Заметка #1", text="текст заметки", data=210124, commentsNotes=[], commentsCount=0, read_comments=0, view_url="URL_1", privacy=2, comment_privacy=2, privacy_view=, privacy_comment=, user_id=1111, offset=0, count=0, sort=true, note_ids=, can_comment=1, arrayOfDeletedComments=[], deleted=false),
            Notes(note_id=2, title="Заметка #2", text="текст заметки", data=100224, commentsNotes=[], commentsCount=0, read_comments=0, view_url="URL_100", privacy=2, comment_privacy=2, privacy_view=, privacy_comment=, user_id=20002, offset=0, count=0, sort=true, note_ids=, can_comment=1, arrayOfDeletedComments=[], deleted=false)]
        val result = NoteService.getNotes("1, 2", 1111, 0, 1, true)
        assertEquals(result, expected)

    }*/


    @Test
    fun getNotesById() {
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

        val result = NoteService.getNotesById(1)
        val expected = "Заметка с id=1 имеет следующие параметры: privacy - 2, comment_privacy - 2, can_comment - 1"
        assertEquals(result, expected)
    }



/*@Test
fun getNotesComments() {
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
        2,
        Comments(0, 0, 1111, "Комментарий №2 к посту 1")
    )
    val result = NoteService.getNotesComments(0, true, 0, 2)
    //val expected =
    assertEquals(result, expected)
}*/

    @Test
    fun restoreNotesComment() {
        NoteService.addNote(
            Notes(
                1,
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
            Comments(1, 0, 1111, "Комментарий №1 к посту 1")
        )
        NoteService.notesCreateComment(
            1,
            Comments(2, 0, 1111, "Комментарий №2 к посту 1")
        )
        NoteService.notesDeleteComment(1, 1)


        val result = NoteService.restoreNotesComment(1, 1)
        val expected = 1
        assertEquals(result, expected)
    }

}