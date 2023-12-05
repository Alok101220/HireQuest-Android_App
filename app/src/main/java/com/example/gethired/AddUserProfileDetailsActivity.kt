package com.example.gethired

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.gethired.Callback.*
import com.example.gethired.Token.TokenManager
import com.example.gethired.ViewModel.*
import com.example.gethired.entities.*
import com.example.gethired.factory.*
import java.util.*

@SuppressLint("InflateParams")
class AddUserProfileDetailsActivity : AppCompatActivity() {

    private lateinit var personalInfoBtn: ImageView
    private lateinit var educationBtn: ImageView
    private lateinit var skillBtn: ImageView
    private lateinit var projectBtn: ImageView
    private lateinit var experienceBtn: ImageView
    private lateinit var certificateBtn: ImageView
    private lateinit var awardBtn: ImageView
    private lateinit var achievementBtn: ImageView
    private lateinit var codingProfileBtn: ImageView
    private lateinit var languageBtn: ImageView
    private lateinit var hobbiesBtn: ImageView

    private lateinit var personalInfoCardView: CardView
    private lateinit var educationCardView: CardView
    private lateinit var skillCardView: CardView
    private lateinit var projectCardView: CardView
    private lateinit var experienceCardView: CardView
    private lateinit var certificateCardView: CardView
    private lateinit var languageCardView: CardView
    private lateinit var awardCardView: CardView
    private lateinit var achievementCardView: CardView
    private lateinit var hobbiesCardView: CardView
    private lateinit var profileLinkCardView: CardView

    //    hidden layouts
    private lateinit var personalInfoHidden: LinearLayout
    private lateinit var educationHidden: LinearLayout

    private lateinit var addMoreSection: Button
    private val filterOptions: MutableList<String> =
        mutableListOf("Award", "Achievement", "Hobbies")

    //    recyclerViews
    private lateinit var educationRecyclerView: RecyclerView
    private lateinit var skillRecyclerView: RecyclerView
    private lateinit var projectRecyclerView: RecyclerView
    private lateinit var experienceRecyclerView: RecyclerView
    private lateinit var certificateRecyclerView: RecyclerView
    private lateinit var languageRecyclerView: RecyclerView
    private lateinit var profileLinkRecyclerView: RecyclerView

    // Adapters
//    private lateinit var educationAdapter: EducationAdapter
//    private lateinit var skillAdapter: SkillAdapter
//    private lateinit var projectAdapter: ProjectAdapter
//    private lateinit var experienceAdapter: ExperienceAdapter
//    private lateinit var certificateAdapter: CertificateAdapter
//    private lateinit var languageAdapter: LanguageAdapter
//    private lateinit var profileLinkAdapter: ProfileLinkAdapter
//

    //    list
    private var educationList: MutableList<Education> = mutableListOf()
//    private var skillList: MutableList<Skill> = mutableListOf()
    private val projectList: MutableList<Project> = mutableListOf()
    private val descriptionList: MutableList<String> = mutableListOf()
    private val experienceList: MutableList<Experience> = mutableListOf()
    private val certificateList: MutableList<Certificate> = mutableListOf()
//    private val languageList: MutableList<Language> = mutableListOf()
    private val profileList: MutableList<Profile> = mutableListOf()

    private lateinit var add_education: Button
    private lateinit var add_project: Button

    private lateinit var empty_education: TextView
    private lateinit var empty_project: TextView
    private lateinit var empty_Experience: TextView
    private lateinit var empty_skill: TextView
    private lateinit var empty_certificate: TextView
    private lateinit var empty_language: TextView
    private lateinit var empty_profileLink: TextView

//    private lateinit var educationViewModel: EducationViewModel
    private lateinit var skillViewModel: SkillViewModel
    private lateinit var projectViewModel: ProjectViewModel
    private lateinit var experienceViewModel: ExperienceViewModel
    private lateinit var userProfileViewModel: UserProfileViewModel
    private lateinit var certificateViewModel: CertificateViewModel
    private lateinit var languageViewModel: LanguageViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var userViewModel: UserViewModel


    private var candidateId = 0
    private var candidateBio: String = ""


    private lateinit var backBtn: ImageView
    private lateinit var deleteBtn: ImageView

    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)

//        tokenManager= TokenManager(this@AddUserProfileDetailsActivity)
//        CommonFunction.SharedPrefsUtil.init(applicationContext)
////        get Recruit profile
//        candidateViewModel=ViewModelProvider(this,CandidateViewModelFactory(tokenManager))[CandidateViewModel::class.java]
//        experienceViewModel=ViewModelProvider(this,ExperienceViewModelFactory(tokenManager))[ExperienceViewModel::class.java]
//        projectViewModel=ViewModelProvider(this,ProjectViewModelFactory(tokenManager))[ProjectViewModel::class.java]
//        skillViewModel = ViewModelProvider(this,SkillViewModelFactory(tokenManager))[SkillViewModel::class.java]
//        educationViewModel=ViewModelProvider(this,EducationViewModelFactory(tokenManager))[EducationViewModel::class.java]
//        certificateViewModel=ViewModelProvider(this,CertificateViewModelFactory(tokenManager))[CertificateViewModel::class.java]
//        languageViewModel=ViewModelProvider(this,LanguageViewModelFactory(tokenManager))[LanguageViewModel::class.java]
//        profileLinkViewModel=ViewModelProvider(this,ProfileLinkViewModelFactory(tokenManager))[ProfileLinkViewModel::class.java]
//        userViewModel = ViewModelProvider(this, UserViewModelFactory(tokenManager))[UserViewModel::class.java]
//
//
////        var userId:Int=
////            CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()!!.id
//
//        val userDto=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
//        if (userDto != null) {
//            candidateViewModel.getCandidateDto(userDto.id.toLong(),object : CandidateCallback {
//                override fun onCandidateAdded(candidateProfileDto: CandidateProfileDto) {
//                    candidateId=candidateProfileDto.id
//                    candidateBio=candidateProfileDto.bio
//                }
//                override fun onCandidateError() {
//
//                }
//
//            })
//        }
//
//
//        backBtn=findViewById(R.id.back_to_profileActivity)
//        backBtn.setOnClickListener {
//            startActivity(Intent(this@AddUserProfileDetailsActivity,ProfileActivity::class.java))
//            finish()
//        }
//
//        deleteBtn=findViewById(R.id.deleteCandidateProfile)
//        deleteBtn.setOnClickListener {
//            val alertDialogBuilder = AlertDialog.Builder(this)
//            alertDialogBuilder.setTitle("Alert")
//                alertDialogBuilder.setMessage("Confirm to delete profile")
//                alertDialogBuilder.setPositiveButton("Confirm") { dialog, _ ->
////                    val userDto=CommonFunction.SharedPrefsUtil.fetchUserResponseFromSharedPreferences()
//                    if (userDto != null) {
//
//                        userDto.isRecuritie=0
//                        userViewModel.updateUserDetails(userDto,userDto.id.toLong(),object :UpdateUserCallback{
//                            override fun onUserUpdated(updatedUserDto: UserDto) {
//                                // Update the userDto with the new data from the API response
//
//                                CommonFunction.SharedPrefsUtil.updateUserResponseFromSharedPreferences(updatedUserDto)
//                                Toast.makeText(this@AddUserProfileDetailsActivity,"Deleted!",Toast.LENGTH_SHORT).show()
//                                startActivity(Intent(this@AddUserProfileDetailsActivity,ProfileActivity::class.java))
//                                finish()
//
//                            }
//
//                            override fun onUpdateUserError() {
//                                Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to delete!",Toast.LENGTH_SHORT).show()
//                            }
//
//                        })
//                    dialog.dismiss()
//                }
//
//            }
//            alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
//                dialog.dismiss()
//            }
//
//            val alertDialog = alertDialogBuilder.create()
//            alertDialog.show()
//
//
//        }
////hidden layouts
//        personalInfoHidden=findViewById(R.id.personal_info_hidden_layout)
//        educationHidden=findViewById(R.id.education_hidden_linearLayout)
//
//
//
////        cardView id's
//        personalInfoCardView=findViewById(R.id.personal_information_cardview)
//        educationCardView=findViewById(R.id.education_cardView)
//        skillCardView=findViewById(R.id.skill_cardView)
//        projectCardView=findViewById(R.id.project_cardView)
//        experienceCardView=findViewById(R.id.experience_cardView)
//        certificateCardView=findViewById(R.id.certificate_cardView)
//        languageCardView=findViewById(R.id.language_cardView)
//        awardCardView=findViewById(R.id.award_cardView)
//        achievementCardView=findViewById(R.id.achievement_cardView)
//        hobbiesCardView=findViewById(R.id.hobbies_cardView)
//        profileLinkCardView=findViewById(R.id.profileLink_cardView)
//
//
////        all the option button
//        personalInfoBtn=findViewById(R.id.option_personal_info)
//        educationBtn=findViewById(R.id.option_education)
//        skillBtn=findViewById(R.id.option_skill)
//        projectBtn=findViewById(R.id.option_project)
//        experienceBtn=findViewById(R.id.option_experience)
//        certificateBtn=findViewById(R.id.option_certificate)
//        awardBtn=findViewById(R.id.option_award)
//        codingProfileBtn=findViewById(R.id.option_profileLink)
//        languageBtn=findViewById(R.id.option_language)
//        achievementBtn=findViewById(R.id.option_achievement)
//        hobbiesBtn=findViewById(R.id.option_hobbies)
//
////       add more button
//        addMoreSection=findViewById(R.id.add_more_section_btn)
//
//        personalInfoBtn.setOnClickListener {
//            showPersonalInfoPopupMenu()
//        }
//        educationBtn.setOnClickListener {
//            showEducationPopupMenu()
//        }
//
//        skillBtn.setOnClickListener {
//           showSkillPopupMenu()
//        }
//        projectBtn.setOnClickListener {
//            showProjectPopupMenu()
//        }
//
//        experienceBtn.setOnClickListener {
//            showExperiencePopupMenu()
//        }
//
//        certificateBtn.setOnClickListener {
//            showCertificatePopupMenu()
//        }
//
//        awardBtn.setOnClickListener {
//            showAwardPopupMenu()
//        }
//
//        codingProfileBtn.setOnClickListener {
//            showProfileLinkPopupMenu()
//        }
//
//        languageBtn.setOnClickListener {
//            showLanguagePopupMenu()
//        }
//        addMoreSection.setOnClickListener {
//            showFilterOptionsDialog()
//        }
//
//        personalInfoCardView.setOnClickListener{
//            if(personalInfoHidden.visibility==View.GONE){
//                personalInfoHidden.visibility=View.VISIBLE
//                personalInfoBtn.setImageResource(R.drawable.icon_up)
//            }
//            else{
//                personalInfoHidden.visibility=View.GONE
//                personalInfoBtn.setImageResource(R.drawable.icon_more)
//            }
//        }
//
//        educationCardView.setOnClickListener {
//            educationRecyclerView=findViewById(R.id.education_recyclerView)
//            empty_education=findViewById(R.id.education_empty)
//
//            if(educationHidden.visibility==View.GONE){
//                educationHidden.visibility=View.VISIBLE
//                educationBtn.setImageResource(R.drawable.icon_up)
//                if(!::educationAdapter.isInitialized){
//                    educationAdapter= EducationAdapter(educationList,this)
//                    educationRecyclerView.adapter=educationAdapter
//                    educationRecyclerView.layoutManager=LinearLayoutManager(this@AddUserProfileDetailsActivity)
//                }
//
//                educationViewModel.getAllEducation(candidateId.toLong()).observe(this){
//                    educationList.clear()
//                    if(it!=null){
//                        educationList.addAll(it)
//                    }
//                    if(educationList.isEmpty()){
//                        empty_education.visibility=View.VISIBLE
//                    }else{
//                        empty_education.visibility=View.GONE
//                    }
//                    educationAdapter.updateData(educationList)
//                    educationAdapter.notifyDataSetChanged()
//                }
//                educationAdapter.setOnItemLongClickListener(object :EducationAdapter.OnItemLongClickListener{
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val educationItem=educationList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId){
//                                R.id.deleteOption -> {
//                                    educationViewModel.deleteEducation(educationItem.id.toLong())
//                                    educationList=educationList.toMutableList().apply { removeAt(position) }
//                                    educationAdapter.updateData(educationList)
//                                    educationAdapter.notifyItemRemoved(position)
//                                    if(educationList.isEmpty()){
//                                        empty_education.visibility=View.VISIBLE
//                                    }
//                                     true
//                                }
//                                R.id.updateOption -> {
//                                    addEducationPopup(educationItem,position)
//                                    true
//                                }
//                                else->{
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//                    }
//
//                })
//
//            }else{
//                empty_education.visibility=View.GONE
//                educationHidden.visibility=View.GONE
//                educationBtn.setImageResource(R.drawable.icon_more)
//            }
//        }
//        skillCardView.setOnClickListener {
//            val horizontal =findViewById<View>(R.id.skill_view_horizontal)
//            skillRecyclerView=findViewById(R.id.skill_recyclerView)
//            empty_skill=findViewById<TextView>(R.id.skill_empty)
//
//
//            if (skillRecyclerView.visibility == View.GONE) {
//                // Show the RecyclerView and populate it with data
//                skillRecyclerView.visibility = View.VISIBLE
//                horizontal.visibility = View.VISIBLE
//                skillBtn.setImageResource(R.drawable.icon_up)
//
//                // Ensure skillAdapter is initialized only once
//                if (!::skillAdapter.isInitialized) {
//                    skillAdapter = SkillAdapter(skillList,this)
//                    skillRecyclerView.adapter = skillAdapter
//                    skillRecyclerView.layoutManager = LinearLayoutManager(this@AddUserProfileDetailsActivity)
//                }
//
//                // Update the data in the adapter and notify changes
//
//                skillViewModel.getSkills(candidateId.toLong()).observe(this) { it ->
//                    skillList.clear()
//                    if (it != null) {
//                        skillList.addAll(it)
//                    }
//                    if(skillList.isEmpty()){
//                        empty_skill.visibility=View.VISIBLE
//                    }else{
//                        empty_skill.visibility=View.GONE
//                    }
//                    // Update the data in the adapter and notify changes
//                    skillAdapter.updateData(skillList)
//                }
//
//                skillAdapter.setOnItemLongClickListener(object : SkillAdapter.OnItemLongClickListener {
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val skillItem = skillList[position]
//
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//
//                        popupMenu.setOnMenuItemClickListener { menuItem ->
//                            when (menuItem.itemId) {
//                                R.id.deleteOption -> {
//                                    // Handle the delete action here
//                                    CommonFunction(this@AddUserProfileDetailsActivity).deleteSkill(skillItem.id)
//                                    skillList = skillList.toMutableList().apply { removeAt(position) }
//                                    skillAdapter.updateData(skillList)
//                                    skillAdapter.notifyItemRemoved(position)
//                                    if(skillList.isEmpty()){
//                                        empty_skill.visibility=View.VISIBLE
//                                    }
//                                    true
//                                }
//                                R.id.updateOption -> {
//                                    // Handle the update action here
//
//                                    addSkillPopup(skillItem.id,skillItem.name,position)
////                                    skillAdapter.notifyItemChanged(position)
//                                    true
//                                }
//                                else -> false
//                            }
//                        }
//
//                        popupMenu.show()
//                    }
//                })
//
//            } else {
//                // Hide the RecyclerView
//                empty_skill.visibility = View.GONE
//                skillRecyclerView.visibility = View.GONE
//                horizontal.visibility = View.GONE
//                skillBtn.setImageResource(R.drawable.icon_more)
//            }
//        }
//        projectCardView.setOnClickListener {
//            val horizontal =findViewById<View>(R.id.project_view_horizontal)
//            projectRecyclerView=findViewById(R.id.project_recyclerView)
//            empty_project=findViewById(R.id.project_empty)
//
//            if(projectRecyclerView.visibility==View.GONE){
//                projectRecyclerView.visibility=View.VISIBLE
//                horizontal.visibility=View.VISIBLE
//                projectBtn.setImageResource(R.drawable.icon_up)
//
//
//                if(!::projectAdapter.isInitialized){
//                    projectAdapter=ProjectAdapter(projectList,this)
//                    projectRecyclerView.adapter=projectAdapter
//                    projectRecyclerView.layoutManager=LinearLayoutManager(this)
//                }
//
//                projectViewModel.getAllProject(candidateId.toLong()).observe(this){
//                    projectList.clear()
//                    if(it!=null){
//                        projectList.addAll(it)
//                    }
//                    if(projectList.isEmpty()){
//                        empty_project.visibility=View.VISIBLE
//                    }else{
//                        empty_project.visibility=View.GONE
//                    }
//                    projectAdapter.updateData(projectList)
//                }
//                projectAdapter.setOnItemLongClickListener(object :ProjectAdapter.OnItemLongClickListener{
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val projectItem=projectList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId){
//                                R.id.deleteOption -> {
//                                    projectViewModel.deleteProject(projectItem.id.toLong())
//                                    projectList.removeAt(position)
//                                    projectAdapter.notifyItemRemoved(position)
//                                    if(projectList.isEmpty()){
//                                        empty_project.visibility=View.VISIBLE
//                                    }
//                                    true
//                                }
//
//                                R.id.updateOption ->{
//                                    addProjectPopup(projectItem,position)
//                                    true
//                                }
//                                else -> {
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//                    }
//
//                })
//            }else{
//                empty_project.visibility=View.GONE
//                projectRecyclerView.visibility=View.GONE
//                horizontal.visibility=View.GONE
//                projectList.removeAll(projectList)
//                descriptionList.removeAll(descriptionList)
//                projectBtn.setImageResource(R.drawable.icon_more)
//            }
//        }
//
//        experienceCardView.setOnClickListener {
//            val horizontal=findViewById<View>(R.id.experience_view_horizontal)
//            experienceRecyclerView=findViewById(R.id.experience_recyclerView)
//            empty_Experience=findViewById(R.id.experience_empty)
//
//            if(experienceRecyclerView.visibility==View.GONE){
//                experienceRecyclerView.visibility=View.VISIBLE
//                horizontal.visibility=View.VISIBLE
//                experienceBtn.setImageResource(R.drawable.icon_up)
//
//                if(!::experienceAdapter.isInitialized){
//                    experienceAdapter=ExperienceAdapter(experienceList,this)
//                    experienceRecyclerView.adapter=experienceAdapter
//                    experienceRecyclerView.layoutManager=LinearLayoutManager(this)
//                }
//
//                experienceViewModel.getAllExperience(candidateId.toLong()).observe(this){
//                    experienceList.clear()
//                    if(it!=null){
//                        experienceList.addAll(it)
//                    }
//                    if(experienceList.isEmpty()){
//                        empty_Experience.visibility=View.VISIBLE
//                    }
//                    else{
//                        empty_Experience.visibility=View.GONE
//                    }
//                    experienceAdapter.updateData(experienceList)
//                }
//                experienceAdapter.setOnItemLongClickListener(object:ExperienceAdapter.OnItemLongClickListener{
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val experienceItem=experienceList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId) {
//                                R.id.deleteOption -> {
//                                    experienceViewModel.deleteExperience(experienceItem.id.toLong())
//                                    experienceList.removeAt(position)
//                                    experienceAdapter.notifyItemRemoved(position)
//                                    if(experienceList.isEmpty()){
//                                        empty_Experience.visibility=View.VISIBLE
//                                    }
//
//                                    true
//                                }
//                                R.id.updateOption -> {
//                                    addExperiencePopup(experienceItem,position)
//                                    true
//                                }
//                                else -> {
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//                    }
//
//                })
//
//            }else{
//                empty_Experience.visibility=View.GONE
//                experienceRecyclerView.visibility=View.GONE
//                experienceList.removeAll(experienceList)
//                descriptionList.removeAll(descriptionList)
//                horizontal.visibility=View.GONE
//                experienceBtn.setImageResource(R.drawable.icon_more)
//            }
//
//        }
//
//        certificateCardView.setOnClickListener {
//            val horizontal=findViewById<View>(R.id.certificate_view_horizontal)
//            certificateRecyclerView=findViewById(R.id.certificate_recyclerView)
//            empty_certificate=findViewById(R.id.certificate_empty)
//
//            if(certificateRecyclerView.visibility==View.GONE){
//                certificateRecyclerView.visibility=View.VISIBLE
//                horizontal.visibility=View.VISIBLE
//                certificateBtn.setImageResource(R.drawable.icon_up)
//
//                if(!::certificateAdapter.isInitialized){
//                    certificateAdapter=CertificateAdapter(certificateList,this)
//                    certificateRecyclerView.adapter=certificateAdapter
//                    certificateRecyclerView.layoutManager=LinearLayoutManager(this)
//                }
//
//                certificateViewModel.getAllCertificate(candidateId.toLong()).observe(this){
//                    certificateList.clear()
//                    if(it!=null){
//                        certificateList.addAll(it)
//                    }
//                    if(certificateList.isEmpty()){
//                        empty_certificate.visibility=View.VISIBLE
//                    }else{
//                        empty_certificate.visibility=View.GONE
//                    }
//                    certificateAdapter.updateData(certificateList)
//                }
//
//                certificateAdapter.setOnItemLongClickListener(object :CertificateAdapter.OnItemLongClickListener {
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val certificateItem=certificateList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId){
//                                R.id.deleteOption ->{
//                                    certificateViewModel.deleteCertificate(certificateItem.id.toLong())
//                                    certificateList.removeAt(position)
//                                    certificateAdapter.notifyItemRemoved(position)
//                                    if(certificateList.isEmpty()){
//                                        empty_certificate.visibility=View.VISIBLE
//                                    }
//                                    true
//                                }
//                                R.id.updateOption ->{
//
//                                    addCertificatePopup(certificateItem,position)
//                                    true
//                                }
//                                else ->{
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//                    }
//                })
//
//            }else{
//                certificateRecyclerView.visibility=View.GONE
//                horizontal.visibility=View.GONE
//                certificateList.removeAll(certificateList)
//                certificateBtn.setImageResource(R.drawable.icon_more)
//                empty_certificate.visibility=View.GONE
//            }
//        }
//
//        languageCardView.setOnClickListener {
//            val horizontal=findViewById<View>(R.id.language_view_horizontal)
//            languageRecyclerView=findViewById(R.id.language_recyclerView)
//            empty_language=findViewById(R.id.language_empty)
//
//
//            if(languageRecyclerView.visibility==View.GONE){
//                languageRecyclerView.visibility=View.VISIBLE
//                horizontal.visibility=View.VISIBLE
//                languageBtn.setImageResource(R.drawable.icon_up)
//
//                if(!::languageAdapter.isInitialized){
//                    languageAdapter=LanguageAdapter(languageList,this)
//                    languageRecyclerView.adapter=languageAdapter
//                    languageRecyclerView.layoutManager=LinearLayoutManager(this)
//                }
//
//                languageViewModel.getAllLanguage(candidateId.toLong()).observe(this){
//                    languageList.clear()
//                    if(it!=null){
//                        languageList.addAll(it)
//                    }
//                    if(languageList.isEmpty()){
//                        empty_language.visibility=View.VISIBLE
//                    }else{
//                        empty_language.visibility=View.GONE
//                    }
//                    languageAdapter.updateData(languageList)
//                }
//
//                languageAdapter.setOnItemLongClickListener(object :LanguageAdapter.OnItemLongClickListener{
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val languageItem=languageList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId){
//                                R.id.deleteOption ->{
//                                    languageViewModel.deleteLanguage(languageItem.id.toLong())
//                                    languageList.removeAt(position)
//                                    languageAdapter.notifyItemRemoved(position)
//                                    if(languageList.isEmpty()){
//                                        empty_language.visibility=View.VISIBLE
//                                    }
//                                    true
//                                }
//                                R.id.updateOption ->{
//
//                                    addLanguagePopup(languageItem,position)
//                                    true
//                                }
//                                else ->{
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//                    }
//
//                })
//
//
//            }else{
//                languageRecyclerView.visibility=View.GONE
//                horizontal.visibility=View.GONE
//                languageBtn.setImageResource(R.drawable.icon_more)
//                languageList.removeAll(languageList)
//                empty_language.visibility=View.GONE
//            }
//        }
//
//        profileLinkCardView.setOnClickListener {
//            profileLinkRecyclerView=findViewById(R.id.profileLink_recyclerView)
//            empty_profileLink=findViewById(R.id.profileLink_empty)
//            val horizontal=findViewById<View>(R.id.profileLink_view_horizontal)
//
//            if(profileLinkRecyclerView.visibility==View.GONE){
//                profileLinkRecyclerView.visibility=View.VISIBLE
//                codingProfileBtn.setImageResource(R.drawable.icon_up)
//                horizontal.visibility=View.VISIBLE
//
//                if(!::profileLinkAdapter.isInitialized){
//                    profileLinkAdapter=ProfileLinkAdapter(profileLinkList,this)
//                    profileLinkRecyclerView.adapter=profileLinkAdapter
//                    profileLinkRecyclerView.layoutManager=LinearLayoutManager(this)
//                }
//                profileLinkViewModel.getAllProfileLink(candidateId.toLong()).observe(this){
//                    profileLinkList.clear()
//                    if(it!=null){
//                        profileLinkList.addAll(it)
//                    }
//                    if(profileLinkList.isEmpty()){
//                        empty_profileLink.visibility=View.VISIBLE
//                    }else{
//                        empty_profileLink.visibility=View.GONE
//                    }
//                    profileLinkAdapter.updateData(profileLinkList)
//                }
//
//                profileLinkAdapter.setOnItemLongClickListener(object :ProfileLinkAdapter.OnItemLongClickListener{
//                    override fun onItemLongClick(view: View, position: Int) {
//                        val profileLinkItem=profileLinkList[position]
//                        val popupMenu = PopupMenu(this@AddUserProfileDetailsActivity, view)
//                        popupMenu.menuInflater.inflate(R.menu.context_menu_layout, popupMenu.menu)
//                        popupMenu.setOnMenuItemClickListener {
//                            when(it.itemId){
//                                R.id.deleteOption ->{
//                                    profileLinkViewModel.deleteProfileLink(profileLinkItem.id.toLong())
//                                    profileLinkList.removeAt(position)
//                                    profileLinkAdapter.notifyItemRemoved(position)
//                                    if(profileLinkList.isEmpty()){
//                                        empty_profileLink.visibility=View.VISIBLE
//                                    }
//                                    true
//                                }
//                                R.id.updateOption ->{
//                                    addProfileLinkPopup(profileLinkItem,position)
//                                    true
//                                }
//                                else ->{
//                                    true
//                                }
//                            }
//                        }
//                        popupMenu.show()
//
//                    }
//
//                })
//            }
//            else{
//                profileLinkRecyclerView.visibility=View.GONE
//                codingProfileBtn.setImageResource(R.drawable.icon_more)
//                horizontal.visibility=View.GONE
//                empty_profileLink.visibility=View.GONE
//                profileLinkList.clear()
//            }
//
//
//        }
//    }
//
//    private fun showFilterOptionsDialog() {
//
//        val builder = AlertDialog.Builder(this,R.style.CustomAlertDialogTheme)
//            .setTitle("Click To Add")
//            .setItems(filterOptions.toTypedArray()) { _, which ->
//                val selectedOption = filterOptions[which]
//                filterList(selectedOption)
//                filterOptions.removeAt(which)
//            }
//        if(filterOptions.size==0){
//            builder.setMessage("Nothing to show")
//        }
//
//        builder.show()
//    }
//
//    private fun filterList(selectedOption: String) {
//
//        when (selectedOption) {
//            "Experience" -> {
//                experienceCardView.visibility=View.VISIBLE
//            }
//            "Coding Profile" -> {
//                profileLinkCardView.visibility=View.VISIBLE
//            }
//            "Award" -> {
//                awardCardView.visibility= View.VISIBLE
//                // No filtering, add all items
//            }
//            "Achievement" -> {
//                achievementCardView.visibility=View.VISIBLE
//            }
//            else -> {
//                hobbiesCardView.visibility=View.VISIBLE
//                // Apply filtering logic based on the selected option
//
//            }
//        }
//        }
//
//    private fun showPersonalInfoPopupMenu() {
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,personalInfoBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addPersonalInfo()
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//    @SuppressLint("InflateParams")
//    private fun addPersonalInfo(){
//        val inflater = LayoutInflater.from(this)
//            val popupView = inflater.inflate(R.layout.add_address_popup, null)
//            val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//            // Set background drawable
//            popupWindow.animationStyle=R.style.PopupAnimation
//            popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//
//// Set outside touch-ability
//            popupWindow.isOutsideTouchable = true
//
//// Set focusability
//            popupWindow.isFocusable = true
//
//            popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//
//            val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//
//        closeBtn.setOnClickListener {
//                popupWindow.dismiss()
//            }
//    }
//
////    EDUCATION PART WORK
//
//    private fun showEducationPopupMenu() {
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,educationBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//
//                    addEducationPopup(Education(0,"","","",""),0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//
//    private fun addEducationPopup(education: Education,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_education_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        // Set background drawable
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//
//// Set outside touch-ability
//        popupWindow.isOutsideTouchable = false
//
//// Set focusability
//        popupWindow.isFocusable = true
//
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//        add_education=popupView.findViewById(R.id.add_education_submit)
//
//        val name=popupView.findViewById<TextView>(R.id.eduaction_name)
//        val to=popupView.findViewById<TextView>(R.id.education_to)
//        val from=popupView.findViewById<TextView>(R.id.education_from)
//        val standard=popupView.findViewById<TextView>(R.id.eduaction_degree)
//        name.text=education.name
//        to.text=education.end
//        from.text=education.start
//        standard.text=education.standard
//        from.setOnClickListener {
//            CommonFunction(this).pickDate(from)
//        }
//        to.setOnClickListener {
//            CommonFunction(this).pickDate(to)
//        }
//
//        add_education.setOnClickListener {
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            add_education.visibility=View.GONE
//            if(education.id<=0){
//                val educationToAdd=Education(0,to.text.toString(), name.text.toString(),standard.text.toString(),from.text.toString())
//
//                educationViewModel.addEducation(educationToAdd,candidateId.toLong(),object :EducationCallback{
//                    override fun onEducationResponse(education: Education) {
//                        if(::educationAdapter.isInitialized){
//                            loadingBar.visibility=View.GONE
//                            add_education.visibility=View.VISIBLE
//                            educationList.add(education)
//                            educationAdapter.notifyItemInserted(educationList.size-1)
//
//                            empty_education.visibility=View.GONE
//                        }
//                        popupWindow.dismiss()
//                    }
//
//                    override fun onEducationError() {
//                        loadingBar.visibility=View.GONE
//                        add_education.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Sorry!, not added",Toast.LENGTH_SHORT).show()
//
//                    }
//
//                })
//            }else{
//                educationViewModel.updateEducation(education, education.id.toLong(),object:EducationCallback{
//
//                    override fun onEducationResponse(education: Education) {
//                        educationList.removeAt(position)
//                        educationList.add(position,education)
//                        educationAdapter.notifyDataSetChanged()
//                        popupWindow.dismiss()
//
//                    }
//
//                    override fun onEducationError() {
//                        popupWindow.dismiss()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Sorry!, not updated",Toast.LENGTH_SHORT).show()
//                    }
//
//                })
//            }
//        }
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    SKILL PART
//
//    private fun showSkillPopupMenu(){
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,skillBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addSkillPopup(0,"",0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//    private fun addSkillPopup(skillId:Int,skillname:String,position: Int) {
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_skill_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        // Set background drawable
//        popupWindow.animationStyle = R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//
//        popupWindow.isOutsideTouchable = true
//
//        // Set focusability
//        popupWindow.isFocusable = true
//
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val closeBtn = popupView.findViewById<ImageView>(R.id.close_popup)
//        val skillEditText = popupView.findViewById<EditText>(R.id.skill_name)
//        val addSkillButton = popupView.findViewById<Button>(R.id.add_skill_submit)
//        skillEditText.setText(skillname)
//        val id=skillId
//
//        addSkillButton.setOnClickListener {
//
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            addSkillButton.visibility=View.GONE
//            val skillName = skillEditText.text.toString().trim()
//
//            if (skillName.isNotEmpty()) {
//                // Create a new Skill object with the given skill name (You may need to adjust the ID)
//                val newSkill = Skill(id, skillName)
//
//                // Add the new skill to the backend or update the data source based on your implementation
//                // For example:
//                if(id<=0){
//
//                    skillViewModel.addSkill(newSkill, candidateId.toLong(),object :AddSkillCallback{
//                        override fun onSkillAdded(skill: Skill) {
//                            if(::skillAdapter.isInitialized){
//                                skillList.add(skill)
//                                skillAdapter.notifyItemInserted(skillList.size - 1)
//                                empty_skill.visibility=View.GONE
//                            }
//                            popupWindow.dismiss()
//                        }
//
//                        override fun onAddSkillError() {
//                            loadingBar.visibility=View.GONE
//                            addSkillButton.visibility=View.VISIBLE
//                            // Show a toast message if there's an error adding the skill
//                            Toast.makeText(this@AddUserProfileDetailsActivity, "Error adding skill! Try again.", Toast.LENGTH_SHORT).show()
//                            popupWindow.dismiss()
//                        }
//
//                    })
//
//                }else{
//
//                    skillViewModel.updateSkill(newSkill, id.toLong(), object : UpdateSkillCallback {
//                        override fun onSkillUpdated(updatedSkill: Skill?) {
//                            // Handle the updated skill object here
//                            loadingBar.visibility=View.GONE
//                            addSkillButton.visibility=View.VISIBLE
//                            if (updatedSkill != null) {
//                                skillList.removeAt(position)
//                                skillList.add(position,updatedSkill)
//                                skillAdapter.notifyDataSetChanged()
//                                popupWindow.dismiss()
//                            } else {
//                                // Skill update failed or an error occurred
//                                // Handle the error scenario
//                                Toast.makeText(this@AddUserProfileDetailsActivity, "Error updating skill! Try again.", Toast.LENGTH_SHORT).show()
//                                popupWindow.dismiss()
//                            }
//                        }
//
//                        override fun onUpdateSkillError() {
//                            // Handle the error scenario when updating the skill
//                            loadingBar.visibility=View.GONE
//                            addSkillButton.visibility=View.VISIBLE
//                        }
//                    })
//
//                }
////
//            } else {
//                loadingBar.visibility=View.GONE
//                loadingBar.playAnimation()
//                addSkillButton.visibility=View.VISIBLE
//                // Show a toast message when the skill name is empty
//                Toast.makeText(this, "Skill name cannot be empty.", Toast.LENGTH_SHORT).show()
//
//            }
//        }
//
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    PROJECT PART
//
//    private  fun showProjectPopupMenu(){
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,projectBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addProjectPopup(Project(0,"","","","",""),0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//    private fun addProjectPopup(project: Project,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_project_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        // Set background drawable
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//// Set outside touch-ability
//        popupWindow.isOutsideTouchable = true
//// Set focusability
//        popupWindow.isFocusable = true
//
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//        add_project=popupView.findViewById(R.id.add_project_submit)
//        val from=popupView.findViewById<TextView>(R.id.project_from)
//        val to=popupView.findViewById<TextView>(R.id.projext_to)
//        val title=popupView.findViewById<EditText>(R.id.project_name)
//        val link=popupView.findViewById<EditText>(R.id.project_link)
//        val description=popupView.findViewById<EditText>(R.id.project_description)
//
//        title.setText(project.title)
//        from.text = project.start
//        to.text = project.end
//        link.setText(project.link)
//        description.setText(project.description)
//        from.setOnClickListener {
//            CommonFunction(this).pickDate(from)
//        }
//        to.setOnClickListener {
//            CommonFunction(this).pickDate(to)
//        }
//
//
//         add_project.setOnClickListener {
//             loadingBar.visibility=View.VISIBLE
//             loadingBar.playAnimation()
//             add_project.visibility=View.GONE
//             val desc:String = description.text.toString()
//             val projectToAdd=Project(project.id,desc,link.text.toString(),title.text.toString(),from.text.toString(),to.text.toString())
//             if(project.id<=0){
//                projectViewModel.addProject(projectToAdd, candidateId.toLong(),object :ProjectCallback{
//                    override fun projectOnResponse(project: Project) {
//                        loadingBar.visibility=View.GONE
//                        add_project.visibility=View.VISIBLE
//                        if(::projectAdapter.isInitialized){
//                            projectList.add(project)
//                            projectAdapter.notifyItemInserted(projectList.size-1)
//                            empty_project.visibility=View.GONE
//                        }
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${projectToAdd.title} added!",Toast.LENGTH_SHORT).show()
//                        popupWindow.dismiss()
//                    }
//
//                    override fun projectOnError() {
//
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to add ${projectToAdd.title}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        add_project.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//            else{
//                projectViewModel.updateProject(projectToAdd, project.id.toLong(),object :ProjectCallback{
//                    override fun projectOnResponse(project: Project) {
//                        loadingBar.visibility=View.GONE
//                        add_project.visibility=View.VISIBLE
//                        projectList.removeAt(position)
//                        projectList.add(position,project)
//                        projectAdapter.notifyDataSetChanged()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${projectToAdd.title} updated!",Toast.LENGTH_SHORT).show()
//                        popupWindow.dismiss()
//                    }
//
//                    override fun projectOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to update ${projectToAdd.title} ",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        add_project.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//        }
//
//
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    EXPERIENCE PART
//
//    private fun showExperiencePopupMenu(){
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,experienceBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addExperiencePopup(Experience(0,"","","","","","",""),0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    experienceCardView.visibility=View.GONE
//                    filterOptions.add("Experience")
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//    private fun addExperiencePopup(experience: Experience,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_experience_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        // Set background drawable
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        popupWindow.isOutsideTouchable = true
//// Set focusability
//        popupWindow.isFocusable = true
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val expTitle=popupView.findViewById<EditText>(R.id.experience_title)
//        val expOrganization=popupView.findViewById<EditText>(R.id.experience_organisation)
//        val expPosition=popupView.findViewById<EditText>(R.id.experience_designation)
//        val expFrom=popupView.findViewById<EditText>(R.id.experience_from)
//        val expTo=popupView.findViewById<EditText>(R.id.experience_to)
//        val expDescription=popupView.findViewById<EditText>(R.id.experience_description)
//        val expLink=popupView.findViewById<EditText>(R.id.experience_link)
//        val submitBtn=popupView.findViewById<Button>(R.id.add_experience_submit)
//
//        expTitle.setText(experience.title)
//        expOrganization.setText(experience.organisation)
//        expPosition.setText(experience.position)
//        expFrom.setText(experience.start)
//        expTo.setText(experience.end)
//        expDescription.setText(experience.description)
//        expLink.setText(experience.link)
//        expFrom.setOnClickListener {
//            CommonFunction(this).pickDate(expFrom)
//        }
//        expTo.setOnClickListener {
//            CommonFunction(this).pickDate(expTo)
//        }
//        submitBtn.setOnClickListener {
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            submitBtn.visibility=View.GONE
//            val experienceToUpdate=Experience(experience.id,expTitle.text.toString(),expOrganization.text.toString(),expPosition.text.toString(),expFrom.text.toString(),expTo.text.toString(),expDescription.text.toString(),expLink.text.toString())
//            if(experience.id>0){
//                experienceViewModel.updateExperience(experienceToUpdate,experience.id.toLong(),object :ExperienceCallback{
//                    override fun onExperienceResponse(experience: Experience) {
//                        experienceList.removeAt(position)
//                        experienceList.add(position,experience)
//                        experienceAdapter.notifyItemInserted(position)
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${experienceToUpdate.title} updated!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        submitBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun onExperienceError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to update ${experienceToUpdate.title}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        submitBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//            else{
//
//                experienceViewModel.addExperience(experienceToUpdate,candidateId.toLong(),object :ExperienceCallback{
//                    override fun onExperienceResponse(experience: Experience) {
//                        if (::experienceAdapter.isInitialized) {
//                            experienceList.add(experience)
//                            experienceAdapter.notifyItemInserted(experienceList.size-1)
//                            empty_Experience.visibility=View.GONE
//                        }
//                        loadingBar.visibility=View.GONE
//                        submitBtn.visibility=View.VISIBLE
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${experienceToUpdate.title} inserted!",Toast.LENGTH_SHORT).show()
//                        popupWindow.dismiss()
//                    }
//
//                    override fun onExperienceError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to insert ${experienceToUpdate.title}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        submitBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//        }
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    CERTIFICATE PART
//    private fun showCertificatePopupMenu(){
//    // Create and show the popup menu
//    val popupMenu = PopupMenu(this,certificateBtn )
//    popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//    popupMenu.setOnMenuItemClickListener { menuItem ->
//        // Handle menu item click events
//        when (menuItem.itemId) {
//            R.id.option1 -> {
//                // Handle option 1 selection
//                addCertificatePopup(Certificate(0,"","","",""),0)
//                true
//            }
//            R.id.option2 -> {
//                // Handle option 2 selection
//                Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                true
//            }
//            else -> false
//        }
//    }
//    popupMenu.show()
//    }
//
//    private fun addCertificatePopup(certificate: Certificate,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_certificate_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        popupWindow.isOutsideTouchable = true
//        popupWindow.isFocusable = true
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val cerTitle=popupView.findViewById<EditText>(R.id.certificate_title)
//        val cerOrganization=popupView.findViewById<EditText>(R.id.certificate_platform)
//        val cerFrom=popupView.findViewById<EditText>(R.id.certificate_from)
//        val cerTo=popupView.findViewById<EditText>(R.id.certificate_to)
//        val cerSubmit=popupView.findViewById<Button>(R.id.add_certificate_submit)
//
//        cerTitle.setText(certificate.title)
//        cerOrganization.setText(certificate.organisation)
//        cerFrom.setText(certificate.start)
//        cerTo.setText(certificate.end)
//
//        cerFrom.setOnClickListener {
//            CommonFunction(this).pickDate(cerFrom)
//        }
//        cerTo.setOnClickListener {
//            CommonFunction(this).pickDate(cerTo)
//        }
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//
//        cerSubmit.setOnClickListener {
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            cerSubmit.visibility=View.GONE
//            val certificateToSubmit=Certificate(certificate.id,cerTitle.text.toString(),cerOrganization.text.toString(),cerFrom.text.toString(),cerTo.text.toString())
//            if(certificate.id>0){
//                certificateViewModel.updateCertificate(certificateToSubmit,certificate.id.toLong(),object:CertificateCallback{
//                    override fun certificateOnResponse(certificate: Certificate) {
//                        certificateList.removeAt(position)
//                        certificateList.add(position,certificate)
//                        certificateAdapter.notifyDataSetChanged()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${certificateToSubmit.title} updated!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        cerSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun certificateOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to update ${certificateToSubmit.title}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        cerSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//            else{
//                certificateViewModel.addCertificate(certificateToSubmit,candidateId.toLong(),object :CertificateCallback{
//                    override fun certificateOnResponse(certificate: Certificate) {
//                        if(::certificateAdapter.isInitialized){
//                            certificateList.add(certificate)
//                            certificateAdapter.notifyItemInserted(certificateList.size-1)
//                            empty_certificate.visibility=View.GONE
//                        }
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${certificateToSubmit.title} inserted!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        cerSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun certificateOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to insert ${certificateToSubmit.title}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        cerSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//        }
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    LANGUAGE PART
//
//    private fun showLanguagePopupMenu(){
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,languageBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addLanguagePopup(Language(0,"",""),0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    Toast.makeText(this,"Sorry this is mandatory section",Toast.LENGTH_LONG).show()
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//    private fun addLanguagePopup(language:Language,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_language_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        popupWindow.isOutsideTouchable = true
//        popupWindow.isFocusable = true
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val lanName=popupView.findViewById<EditText>(R.id.language_name)
//        val lanProfeciency=popupView.findViewById<Spinner>(R.id.language_profeciency)
//        val lanSubmit=popupView.findViewById<Button>(R.id.add_language_submit)
//        lanName.setText(language.name)
//
////
//        val languages = resources.getStringArray(R.array.languages)
//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, languages)
//
//        // Step 4: Set the adapter to the Spinner
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//        lanProfeciency.adapter = adapter
//
//        // Step 5 (Optional): Add an OnItemSelectedListener to handle item selection events
//        var profeciency_level=language.proficencyLevel
//        when(profeciency_level){
//            "Beginner"->{
//                lanProfeciency.setSelection(0)
//            }
//            "Intermediate"->{
//                lanProfeciency.setSelection(1)
//            }
//            "Expert"->{
//                lanProfeciency.setSelection(2)
//            }
//            else -> {
//                lanProfeciency.setSelection(0)
//            }
//        }
//        lanProfeciency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedLanguage = languages[position]
//                profeciency_level=selectedLanguage
//            }
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                profeciency_level=language.proficencyLevel
//            }
//        }
//
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//        lanSubmit.setOnClickListener {
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            lanSubmit.visibility=View.GONE
//            val languageToSubmit=Language(language.id,lanName.text.toString(),profeciency_level)
//            if(language.id>0){
//                languageViewModel.updateLanguage(languageToSubmit,language.id.toLong(), object :LanguageCallback{
//                    override fun languageOnResponse(language: Language) {
//                        languageList.removeAt(position)
//                        languageList.add(position,language)
//                        languageAdapter.notifyDataSetChanged()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${languageToSubmit.name} updated!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        lanSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun languageOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to update ${languageToSubmit.name}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        lanSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//                })
//            }else{
//                languageViewModel.addLanguage(languageToSubmit, candidateId.toLong(),object :LanguageCallback{
//                    override fun languageOnResponse(language: Language) {
//                        if(::languageAdapter.isInitialized){
//                            languageList.add(language)
//                            languageAdapter.notifyItemInserted(languageList.size-1)
//                            empty_language.visibility=View.GONE
//                        }
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${languageToSubmit.name} inserted!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        lanSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun languageOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to insert ${languageToSubmit.name}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        lanSubmit.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//        }
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    CODING PROFILE
//
//    private fun showProfileLinkPopupMenu(){
//        // Create and show the popup menu
//        val popupMenu = PopupMenu(this,codingProfileBtn )
//        popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { menuItem ->
//            // Handle menu item click events
//            when (menuItem.itemId) {
//                R.id.option1 -> {
//                    // Handle option 1 selection
//                    addProfileLinkPopup(ProfileLink(0,"",""),0)
//                    true
//                }
//                R.id.option2 -> {
//                    // Handle option 2 selection
//                    profileLinkCardView.visibility=View.GONE
//                    filterOptions.add("Coding Profile")
//                    true
//                }
//                else -> false
//            }
//        }
//
//        popupMenu.show()
//    }
//
//    private fun addProfileLinkPopup(profileLink: ProfileLink,position: Int){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_profilelink_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        popupWindow.isOutsideTouchable = true
//        popupWindow.isFocusable = true
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//        val loadingBar=popupView.findViewById<LottieAnimationView>(R.id.loading_animation)
//        val handleName=popupView.findViewById<EditText>(R.id.profileLink_name)
//        val url=popupView.findViewById<EditText>(R.id.profileLink_Url)
//        val profileLinkBtn=popupView.findViewById<Button>(R.id.add_profileLink_submit)
//
//        handleName.setText(profileLink.handleName)
//        url.setText(profileLink.link)
//
//        profileLinkBtn.setOnClickListener {
//
//            loadingBar.visibility=View.VISIBLE
//            loadingBar.playAnimation()
//            profileLinkBtn.visibility=View.GONE
//            val profileLinkToSubmit=ProfileLink(profileLink.id, handleName.text.toString(),url.text.toString())
//            if(profileLink.id>0){
//                profileLinkViewModel.updateProfileLink(profileLinkToSubmit,profileLink.id.toLong(),object :ProfileLinkCallback{
//                    override fun profileLinkOnResponse(profileLink: ProfileLink) {
//                        profileLinkList.removeAt(position)
//                        profileLinkList.add(position,profileLink)
//                        profileLinkAdapter.notifyDataSetChanged()
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${profileLinkToSubmit.handleName} updated!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        profileLinkBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//
//                    }
//
//                    override fun profileLinkOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to update ${profileLinkToSubmit.handleName}!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        profileLinkBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }else{
//                profileLinkViewModel.addProfileLink(profileLinkToSubmit,candidateId.toLong(),object :ProfileLinkCallback{
//                    override fun profileLinkOnResponse(profileLink: ProfileLink) {
//                        if(::profileLinkAdapter.isInitialized){
//                            profileLinkList.add(profileLink)
//                            profileLinkAdapter.notifyItemInserted(profileLinkList.size-1)
//                            empty_profileLink.visibility=View.GONE
//                        }
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"${profileLinkToSubmit.handleName} inserted!",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        profileLinkBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                    override fun profileLinkOnError() {
//                        Toast.makeText(this@AddUserProfileDetailsActivity,"Failed to insert ${profileLinkToSubmit.handleName} !",Toast.LENGTH_SHORT).show()
//                        loadingBar.visibility=View.GONE
//                        profileLinkBtn.visibility=View.VISIBLE
//                        popupWindow.dismiss()
//                    }
//
//                })
//            }
//        }
//
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
////    AWARD PART
//    private fun showAwardPopupMenu(){
//    // Create and show the popup menu
//    val popupMenu = PopupMenu(this,awardBtn )
//    popupMenu.menuInflater.inflate(R.menu.edit_user_profile_more_option, popupMenu.menu)
//
//    popupMenu.setOnMenuItemClickListener { menuItem ->
//        // Handle menu item click events
//        when (menuItem.itemId) {
//            R.id.option1 -> {
//                // Handle option 1 selection
//                addAwardPopup()
//                true
//            }
//            R.id.option2 -> {
//                // Handle option 2 selection
//                awardCardView.visibility=View.GONE
//                filterOptions.add("Award")
//                true
//            }
//            else -> false
//        }
//    }
//
//    popupMenu.show()
//    }
//
//    private fun addAwardPopup(){
//        val inflater = LayoutInflater.from(this)
//        val popupView = inflater.inflate(R.layout.add_award_popup, null)
//        val popupWindow = PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        popupWindow.animationStyle=R.style.PopupAnimation
//        popupWindow.setBackgroundDrawable(ColorDrawable(Color.WHITE))
//        popupWindow.isOutsideTouchable = true
//        popupWindow.isFocusable = true
//        popupWindow.showAtLocation(this.findViewById(R.id.add_details_activity), Gravity.BOTTOM, 0, 0)
//
//        val closeBtn=popupView.findViewById<ImageView>(R.id.close_popup)
//
//        closeBtn.setOnClickListener {
//            popupWindow.dismiss()
//        }
//    }
//
//}

    }
}