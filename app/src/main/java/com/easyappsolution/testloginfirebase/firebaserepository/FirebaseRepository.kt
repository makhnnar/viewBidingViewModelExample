package com.easyappsolution.testloginfirebase.firebaserepository

import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import com.easyappsolution.testloginfirebase.R
import com.google.firebase.database.*

class FirebaseRepository(
    var fireDbInstance: FirebaseDatabase,
    var context: Context
) : ChildEventListener {

    val TAG = "FirebaseRepository"

    var questionList : MutableList<QuestionModel?> = mutableListOf()

    lateinit var listener: FirebaseRepoListener

    var globalRef: DatabaseReference? = null

    var globalOneRef: DatabaseReference? = null

    var singleRef:DatabaseReference? = null

    var singleListener:ValueEventListener? = null

    var singleGlobalListener:ValueEventListener? = null

    private var counter : CountDownTimer? = null

    private var dataQuery : Query? = null

    companion object {
        lateinit var instance: FirebaseRepository private set
    }

    init{
        instance = this
    }

    fun onListenThisSurvey(idSurvey:Int,listener: FirebaseRepoListener){
        if(singleRef==null){
            //singleRef = fireDbInstance.getReference("encuesta_rt_dimayor/${idSurvey}")
            singleRef = fireDbInstance.getReference("encuesta_rt/${idSurvey}")
            singleListener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {

                    try{
                        val value = dataSnapshot.getValue<QuestionModel>(QuestionModel::class.java)

                        Log.d(TAG, "Encuesta activa: ${value.toString()}")

                        if(value!=null){
                            listener.onReceiveSurveyData(value)
                        }else{
                            listener.isThereAnySurvey(false)
                        }
                    }catch (e:Exception){

                        listener.isThereAnySurvey(false)
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read porcentaje.", error.toException())
                }

            }
            singleRef?.addValueEventListener(singleListener)
        }
    }

    fun removerListenThisSurvey(){
        try{
            if(singleRef!=null){
                singleRef?.removeEventListener(singleListener)
                singleListener = null
                singleRef = null
                dataQuery = null
            }
        }catch (e:Exception){

        }
    }


    fun onReceiveOneSurvey(listener: FirebaseRepoListener){
        if(globalOneRef==null){
            globalOneRef = fireDbInstance.getReference("encuesta_rt")
            initQueryReference()
            //globalOneRef = fireDbInstance.getReference("encuesta_rt_dimayor")
            singleGlobalListener = object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    try{
                        if(dataSnapshot.childrenCount>0){
                            if(counter!=null){
                                //cancelamos el conteo anterior si recibimos una actualizacion de tiempo
                                counter?.cancel()
                            }
                            counter = object : CountDownTimer(2000,1000){
                                override fun onTick(millisUntilFinished: Long) {

                                }

                                override fun onFinish() {
                                    listener.isThereAnySurvey(true)
                                }
                            }.start()
                        }else{
                            listener.isThereAnySurvey(false)
                        }
                    }catch (e:Exception){

                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(TAG, "Failed to read porcentaje.", error.toException())
                }

            }
            //globalOneRef?.addValueEventListener(singleGlobalListener)
            dataQuery?.addValueEventListener(singleGlobalListener)
        }
    }

    fun removerSingleGlobalRef(){
        try{
            if(globalOneRef!=null){
                globalOneRef?.removeEventListener(singleGlobalListener)
                singleGlobalListener = null
                globalOneRef = null
                dataQuery = null
            }
        }catch (e:Exception){

        }
    }

    fun setGlobalListener(listener: FirebaseRepoListener){
        this.listener = listener
        if(globalRef==null){
            //globalRef = fireDbInstance.getReference("encuesta_rt_dimayor")
            globalRef = fireDbInstance.getReference("encuesta_rt")
            //globalRef?.addChildEventListener(this)
            initQueryReference()
            dataQuery?.addChildEventListener(this)
        }
    }

    fun removerGlobalRef(){
        try{
            if(globalRef!=null){
                globalRef?.removeEventListener(this)
                globalRef = null
                dataQuery = null
            }
        }catch (e:Exception){

        }
    }

    override fun onCancelled(dataSnapshot: DatabaseError?) {

    }

    override fun onChildMoved(dataSnapshot: DataSnapshot?, previousChildName: String?) {
        try{
            val value = dataSnapshot?.getValue<QuestionModel>(QuestionModel::class.java)
        }catch (e:Exception){

        }
    }

    /**
     * Por ahora este metodo devuelve el primer elemento
     */
    override fun onChildChanged(dataSnapshot: DataSnapshot?, previousChildName: String?) {
        try{
            val value = dataSnapshot?.getValue<QuestionModel>(QuestionModel::class.java)
            questionList.forEach{
                if(it?.id == value?.id){
                    questionList[questionList.indexOf(it)] = value
                }
            }
            if(dataSnapshot?.childrenCount!!>0){
                listener.onReceiveSurveyData(questionList[0])
            }else{
                listener.isThereAnySurvey(false)
            }
        }catch (e:Exception){

            listener.isThereAnySurvey(false)
        }
    }

    /**
     * Por ahora este metodo devuelve el primer elemento
     */
    override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildName: String?) {
        try{
            val value = dataSnapshot?.getValue<QuestionModel>(QuestionModel::class.java)
            questionList.add(value)
            if(dataSnapshot?.childrenCount!!>0){
                listener.onReceiveSurveyData(questionList[0])
            }else{
                listener.isThereAnySurvey(false)
            }
        }catch (e:Exception){

            listener.isThereAnySurvey(false)
        }
    }

    /**
     * Por ahora este metodo devuelve el primer elemento
     */
    override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
        try{
            val value = dataSnapshot?.getValue<QuestionModel>(QuestionModel::class.java)
            questionList.forEach{
                if(it?.id == value?.id){
                    questionList.removeAt(questionList.indexOf(it))
                }
            }
            if(dataSnapshot?.childrenCount!!>0){
                listener.onReceiveSurveyData(questionList[0])
            }else{
                listener.isThereAnySurvey(false)
            }
        }catch (e:Exception){

            listener.isThereAnySurvey(false)
        }
    }

    private fun initQueryReference(){
        if(dataQuery==null){
            dataQuery = globalRef?.orderByChild("app_name")?.equalTo(
                Utils.getString(
                    context,
                    R.string.firebase_reference
                )
            )
        }
    }
}