package ru.netology

import java.util.*
import kotlin.Comparator

class PostNotFoundException(message: String) : RuntimeException(message)

data class Comments(
    var commentId: Int = 0,
    var from_id: Int = 0,
    var date: Int = 0,
    var text: String = "",
    var deleted: Boolean = false // комментарий по умолчанию виден!
)

data class Notes(
    var note_id: Int = 0,
    var title: String = "",
    var text: String = "",
    var data: Int,
    var commentsNotes: Array<Comments> = emptyArray(),
    var commentsCount: Int = 0, //кол-во комментариев
    var read_comments: Int = 0, //кол-во прочитанных комментариев
    var view_url: String = "",
    var privacy: Int, // Уровень доступа к заметке. Возможные значения: 0 - все пользователи, 1 -только друзья, 2 -  друзья и друзья друзей, 3 -только пользователь
    var comment_privacy: Int, // Уровень доступа к комментариям в заметке. Возможные значения: 0 - все пользователи, 1 -только друзья, 2 -  друзья и друзья друзей, 3 -только пользователь
    var privacy_view: String = "",
    var privacy_comment: String = "",
    var user_id: Int, //Идентификатор пользователя, информацию о заметках которого требуется получить.
    var offset: Int, //Смещение, необходимое для выборки определенного подмножества заметок.
    var count: Int = 20, //Количество заметок, информацию о которых необходимо получить.до 100!
    var sort: Boolean, // 0 по убыванию, 1 - по возрастанию
    var note_ids: String = "",
    var can_comment: Int, // in 1..2?
    var arrayOfDeletedComments: Array<Comments> = emptyArray(),
    var deleted: Boolean = false // заметка по умолчанию видна!

)

object NoteService {
    private var notes = emptyArray<Notes>() //массив для хранения заметок
    private var lastNotesId = 0

    private var commentsNotes = emptyArray<Comments>() //массив для хранения комментариев к заметкам
    private var lastCommentsNotesId = 0


    fun printNotes() {
        for (note in notes) {
            print(note)
            println(" ")
            println()
        }
    }

    fun clearNotes() {
        notes = emptyArray()
        lastNotesId = 0
    }

    fun clearComments() {
        commentsNotes = emptyArray()
        lastCommentsNotesId = 0
    }

    fun addNote(note: Notes): Int {
        notes += note.copy(++lastNotesId) //добавляет заметку в массив
        if (notes.isNotEmpty() && notes.last().note_id == lastNotesId) {
            return notes.last().note_id
        }
        throw PostNotFoundException(
            "Произошла неизвестная ошибка. Заметка не создана!"
        )
    }

    fun notesCreateComment(note_id: Int, comment: Comments): Int {
        for ((index, note) in notes.withIndex()) {
            if (note.note_id == note_id) {
                commentsNotes += comment.copy(commentId = lastCommentsNotesId++)
                notes[index] = note.copy(commentsNotes = note.commentsNotes + commentsNotes.last())
                return commentsNotes.last().commentId
            }
        }
        throw PostNotFoundException("182 You can't comment this note. Заметки с таким id $note_id нет!")
    }

    fun deleteNote(note_id: Int): Int {
        for ((index, note) in notes.withIndex()) {
            if (note.note_id == note_id) {
                val arrList = notes.toMutableList()
                arrList.removeAt(note_id - 1)
                notes = arrList.toTypedArray()
                lastNotesId--
                note.deleted = true
                return 1
            }
        }

        throw IndexOutOfBoundsException("180 Note not found. Заметку с таким id $note_id невозможно удалить!")
    }

    fun notesDeleteComment(comment_id: Int): Int {

        for ((index, comment) in commentsNotes.withIndex()) {
            if (comment.commentId == comment_id) {
                val arrList = commentsNotes.toMutableList()
                arrList.removeAt(comment_id - 1)
                commentsNotes = arrList.toTypedArray()
                lastCommentsNotesId--
                comment.deleted = true
                return 1
            }
        }

        throw IndexOutOfBoundsException("181 Access to note denied. Комментарий с таким id $comment_id невозможно удалить!")
    }

    fun editNote(
        note_id: Int,
        title: String,
        text: String,
        privacy: Int,
        comment_privacy: Int,
        privacy_view: String,
        privacy_comment: String
    ): Int {

        for ((index, note) in notes.withIndex()) {
            if (note.note_id == note_id) {

                notes[index] = note.copy(
                    title = title,
                    text = text,
                    privacy = privacy,
                    comment_privacy = comment_privacy,
                    privacy_view = privacy_view,
                    privacy_comment = privacy_comment
                )
                return 1
            }
        }
        throw IndexOutOfBoundsException("180 Note not found. Заметка с id $note_id не найдена!")
    }

    fun notesEditComment(note_id: Int, commentId: Int, message: String): Int {

        for ((index, note) in notes.withIndex()) {
            if (note.note_id == note_id) {
                for ((index, comment) in commentsNotes.withIndex()) {
                    if (comment.commentId == commentId) {
                        commentsNotes[index] = comment.copy(text = message)
                        return 1
                    }
                }
            }
        }

        throw PostNotFoundException("183 Access to comment denied. Комментария с id $note_id нет!")
    }

    fun getNotes(note_ids: String, user_id: Int, offset: Int, count: Int, sort: Boolean) : List<Notes> {

        var resultListNotes: List<Notes> = emptyList()
        var arrayId = note_ids.split(",").toTypedArray() // массив заданный id из строки
        var localCount = 0

        for ((index, stringId) in arrayId.withIndex()) {
            var note_id_1 = stringId[index].toInt()  // то что было String => Int
            for (note in notes) {
                if (note.note_id == note_id_1) { //нашли в массиве нужную заметку
                    if (note.user_id == user_id && localCount <= count) { // и id автора совпали, и еще не перешагнули за предел кол-во заметок
                        resultListNotes += note  //добавляем заметку в новый массив
                        println(resultListNotes.size)
                        localCount++ // увеличиваем счетчик

                    }
                }
            }
        }


        /*val myComparator =  Comparator<Notes> { data1: Notes, data2: Notes ->
            when {
                (data1.note_id > data2.note_id) -> 1
                (data1.note_id < data2.note_id) -> -1
                else -> 0
            }
        }
        Collections.sort(resultListNotes, myComparator)
        return resultListNotes*/
        val noteComparatorSort = Comparator { data1: Notes, data2: Notes ->
            data1.note_id.compareTo(data2.note_id) *
                    data1.data.compareTo(data2.data)
        }
        //Collections.sort(resultListNotes, noteComparatorSort)

        val noteComparatorRevers = Comparator { data2: Notes, data1: Notes ->
            data2.note_id.compareTo(data1.note_id) *
                    data2.data.compareTo(data1.data)
        }
        //Collections.sort(resultListNotes, noteComparatorRevers)

        if (sort) {
            Collections.sort(resultListNotes, noteComparatorSort)
            println(resultListNotes)
            return resultListNotes

        } else {
            Collections.sort(resultListNotes, noteComparatorRevers)
            println(resultListNotes)
            return resultListNotes
        }

    }

    fun getNotesById(note_id: Int): String {
        for (note in notes) {
            if (note.note_id == note_id) {
                return "Заметка с id=$note_id имеет следующие параметры: privacy - ${note.privacy}, comment_privacy - ${note.comment_privacy}, can_comment - ${note.can_comment}"
            }
        }
        throw PostNotFoundException("183 Access to comment denied. Заметки с id $note_id нет!")
    }

    fun getNotesComments(note_id : Int, sort: Boolean, offset: Int, count: Int): Array<Comments> {

        var newArrayOfComments: Array<Comments> = emptyArray()

        for(note in notes) {
            if(note.note_id == note_id) {
                newArrayOfComments += note.commentsNotes
                newArrayOfComments.forEach { println(it) }
                return newArrayOfComments
            }
        }
        /*val noteComparatorSort = Comparator { data1: Int, data2: Int -> data1 - data2 }
        val noteComparatorRevers = Comparator { data1: Int, data2: Int -> data2 - data1 }

        when (sort) {
            1 -> newArrayOfComments.sortedDescending(noteComparatorRevers)
            else -> newArrayOfComments.sorted(noteComparatorSort)
        }

        if(newArrayOfComments.size <= count) {
            return newArrayOfComments
        }*/
        throw PostNotFoundException("181 Access to note denied")
    }

    fun restoreNotesComment(comment_id: Int): Int {

                for (comment in commentsNotes) {
                    if (comment.commentId == comment_id) {
                       if(comment.deleted) {
                           comment.deleted == false
                           println(comment)
                           return 1
                       }
                    }
                }

        throw PostNotFoundException("183 Access to comment denied")
    }
}


fun main() {

    println(
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
                user_id = 1111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
    )

    println(
        NoteService.addNote(
            Notes(
                255,
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
    )

    println(
        NoteService.addNote(
            Notes(
                100500,
                "Заметка #3",
                "текст заметки",
                100224,
                commentsCount = 0,
                read_comments = 0,
                view_url = "URL_100000",
                privacy = 2,
                comment_privacy = 2,
                user_id = 1111,
                offset = 0,
                sort = true,
                count = 0,
                can_comment = 1
            )
        )
    )

    NoteService.printNotes()

    println("----------ADD COMMENTS-------------")

    println(
        NoteService.notesCreateComment(
            1,
            Comments(0, 0, 1111, "Комментарий №1 к посту 1")
        )
    )
    println(
        NoteService.notesCreateComment(
            1,
            Comments(0, 0, 2222, "Комментарий №2 к посту 1")
        )
    )
    println(
        NoteService.notesCreateComment(
            2,
            Comments(0, 0, 3333, "Комментарий №1 к посту 2")
        )
    )
    println(
        NoteService.notesCreateComment(
            3,
            Comments(0, 0, 3333, "Комментарий №1 к посту 3")
        )
    )
    NoteService.printNotes()

    println("----------DELETE NOTE-------------")

    println(NoteService.deleteNote(2))
    NoteService.printNotes()

    println("----------DELETE COMMENTS IN NOTE-------------")

    println(NoteService.notesDeleteComment(3))
    println(NoteService.printNotes())

    println("----------EDIT NOTES-------------")

    println(NoteService.editNote(3, "NEW TITLE", "NEW TEXT", 1, 1, "4654", "123"))
    NoteService.printNotes()

    println("----------EDIT COMMENTS IN NOTES-------------")
    println(NoteService.notesEditComment(3,1, "EDIT COMMENTS"))
    NoteService.printNotes()

    println("----------GET NOTES-------------")
    println(NoteService.getNotes("1", 1111, 0, 1, true))

    println("----------GET NOTES BY ID-------------")
    println(NoteService.getNotesById(1))

    println("----------GET NOTES COMMENTS-------------")
    println(NoteService.getNotesComments(1, true, 0, 2))

    println("--------RESTORE NOTES COMMENT---------")
    println(NoteService.restoreNotesComment(3))

}

