
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.gethired.Callback.EducationCallback
import com.example.gethired.Repository.EducationRepository
import com.example.gethired.Token.TokenManager
import com.example.gethired.entities.Education

class EducationViewModel(tokenManager: TokenManager) : ViewModel() {
    private val educationRepository= EducationRepository(tokenManager)

    fun addEducation(education: Education,candidateId:Long,callback:EducationCallback){
        educationRepository.addEducation(candidateId,education,object :EducationCallback{
            override fun onEducationResponse(education: Education) {
                callback.onEducationResponse(education)
            }

            override fun onEducationError() {
                callback.onEducationError()
            }

        })
    }
    fun getAllEducation(candidateProfileId:Long): LiveData<List<Education> >{
        return educationRepository.getAllEducation(candidateProfileId)
    }
    fun deleteEducation(educationId:Long){
        return educationRepository.deleteEducation(educationId)
    }
    fun updateEducation(education:Education,educationId:Long,callback: EducationCallback){
        educationRepository.updateEducation(education,educationId,object :EducationCallback{
            override fun onEducationResponse(education: Education) {
                callback.onEducationResponse(education)
            }

            override fun onEducationError() {
                callback.onEducationError()
            }

        })
    }

}