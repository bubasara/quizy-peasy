package bubasara.quizypeasy.viewmodels

import androidx.lifecycle.ViewModel

class CreateNewCategoryViewModel : ViewModel() {

    /*  list of questions
        arraylist consists of
        Map < Map <question, list of answers>, correct answer >
     */
    var listOfQuestions = arrayListOf<Map<Map<String, ArrayList<String>>, Int>>()

    //for testing purposes, delete later
    init {
        makeTestingListOfQuestions()
    }

    private fun makeTestingListOfQuestions(){
        var tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        var tempQuestion = "Question no 1"
        var tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))

        tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        tempQuestion = "Question no 2"
        tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))

        tempArrayList = arrayListOf("Answer 1", "Answer 2", "Answer 3", "Answer 4")
        tempQuestion = "Question no 3"
        tempHashMap = hashMapOf(Pair(tempQuestion, tempArrayList))
        listOfQuestions.add(hashMapOf(Pair(tempHashMap, 1)))
    }
}