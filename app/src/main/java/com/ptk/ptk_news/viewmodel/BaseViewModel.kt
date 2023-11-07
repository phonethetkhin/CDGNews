package com.ptk.ptk_news.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ptk.ptk_news.db.entity.ArticleEntity
import com.ptk.ptk_news.db.entity.SourceEntity
import com.ptk.ptk_news.repository.ArticleRepository
import com.ptk.ptk_news.ui.ui_states.ArticleUIStates
import com.ptk.ptk_news.util.datastore.MyDataStore
import com.ptk.ptk_news.util.showToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor(
    private val repository: ArticleRepository,
    private val context: Application,
    private val dataStore: MyDataStore,

    ) : ViewModel() {

    val _uiStates = MutableStateFlow(ArticleUIStates())
    val uiStates = _uiStates.asStateFlow()

    //=======================================states function======================================//

    fun toggleSelectedCategory(categoryId: Int) {
        _uiStates.update { it.copy(selectedCategory = categoryId) }
    }

    suspend fun toggleSelectedFilterBySource(isFilterBySource: Boolean) {
        _uiStates.update { it.copy(isFilterBySource = isFilterBySource) }

        val categoryId = getPreferredCategory() ?: 0
        val countryId = getPreferredCountry()
        val country =
            _uiStates.value.availableCountries.find { it.id == countryId }?.name
                ?: "United States"
        val sources = getPreferredSources()

        if (isFilterBySource) {
            if (_uiStates.value.availableSources.isNotEmpty()) {
                if (sources!!.isNotEmpty()) {
                    val sourcesList = sources.split(",")

                    sourcesList.forEach {
                        toggleInitialSelectedSources(it)
                    }
                }
            }
        } else {
            _uiStates.update {
                it.copy(
                    availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->
                        details.copy(selected = false)

                    } as ArrayList<SourceEntity>)
            }

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)
        }


    }

    fun toggleSelectedCountry(selectedCountry: String) {
        _uiStates.update { it.copy(selectedCountry = selectedCountry) }
    }

    fun toggleSearchValueChange(searchValue: String) {
        _uiStates.update { it.copy(searchText = searchValue, articleSearchText = searchValue) }
    }

    fun toggleIsShowDCDialog(isShowDCDialog: Boolean) {
        _uiStates.update { it.copy(isShowDisconnectedDialog = isShowDCDialog) }
    }

    fun toggleCommentBoxDialog(isShowCommentDialog: Boolean, articleId: Int) {
        _uiStates.update {
            it.copy(
                showCommentDialog = isShowCommentDialog,
                selectedArticleId = articleId
            )
        }
        if (isShowCommentDialog) {
            getExistingComment()
        } else {
            _uiStates.update { it.copy(commentList = arrayListOf(), commentText = "") }
        }
    }

    fun toggleCommentText(cmtText: String) {
        _uiStates.update { it.copy(commentText = cmtText) }
    }

    fun setArticleEntity(articleEntity: ArticleEntity) {
        _uiStates.update { it.copy(articleEntity = articleEntity) }
    }

    fun getExistingComment() {
        viewModelScope.launch {
            val articleId = _uiStates.value.selectedArticleId
            val article = repository.getArticleByIdDB(articleId)
            val commentList = arrayListOf<String>()
            if (!article?.postComment.isNullOrEmpty()) {
                val splitList = article!!.postComment?.split(",")
                if (!splitList.isNullOrEmpty()) {
                    commentList.addAll(splitList.toCollection(ArrayList()))
                    _uiStates.update {
                        it.copy(
                            commentList = commentList,
                            recompose = !_uiStates.value.recompose
                        )
                    }
                }
            }
        }
    }

    fun postComment() {
        viewModelScope.launch {
            val articleId = _uiStates.value.selectedArticleId
            val article = repository.getArticleByIdDB(articleId)
            val commentList = arrayListOf<String>()
            if (!article?.postComment.isNullOrEmpty()) {
                val splitList = article!!.postComment?.split(",")
                if (!splitList.isNullOrEmpty()) {
                    commentList.addAll(splitList.toCollection(ArrayList()))
                }
            }

            val post = _uiStates.value.commentText
            commentList.add(post)

            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm aaa")
            val commentTime = sdf.format(Date())
            repository.updateCommentDB(commentList.joinToString(","), commentTime, articleId)
            _uiStates.update {
                it.copy(
                    commentList = commentList,
                    recompose = !_uiStates.value.recompose
                )
            }
        }
    }

    fun toggleSource(source: String) {
        Log.e("ajsdfklajsdkf", source.toString())
        _uiStates.update { it.copy(source = source) }

        if (source.trim().isNotEmpty()) {

            val suggestionsList = _uiStates.value.availableSources.filter {
                it.name?.toLowerCase()?.contains(source.toLowerCase()) ?: false
            }

            if (suggestionsList.isNotEmpty()) {
                _uiStates.update {
                    it.copy(
                        sourceSuggestions = suggestionsList.map { sugg -> sugg.name!! }
                            .toCollection(ArrayList())
                    )
                }
            } else {
                _uiStates.update {
                    it.copy(
                        sourceSuggestions = arrayListOf()
                    )
                }
            }
        } else {
            _uiStates.update {
                it.copy(
                    sourceSuggestions = arrayListOf()
                )
            }
        }
    }

    fun toggleSelectedSources(selectedSource: String) {
        val selectedSourceItem = _uiStates.value.availableSources.find { it.name == selectedSource }

        _uiStates.update { uiStates ->
            uiStates.copy(
                source = "",
                sourceSuggestions = arrayListOf(),
                availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->

                    if (_uiStates.value.availableSources.indexOf(_uiStates.value.availableSources.find { it.id == selectedSourceItem?.id }) == index)
                        details.copy(selected = !details.selected)
                    else details

                } as ArrayList<SourceEntity>)
        }

    }

    fun toggleInitialSelectedSources(selectedSource: String) {
        val selectedSourceItem = _uiStates.value.availableSources.find { it.id == selectedSource }

        _uiStates.update { uiStates ->
            uiStates.copy(
                source = "",
                sourceSuggestions = arrayListOf(),
                availableSources = _uiStates.value.availableSources.mapIndexed { index, details ->

                    if (_uiStates.value.availableSources.indexOf(_uiStates.value.availableSources.find { it.id == selectedSourceItem?.id }) == index)
                        details.copy(selected = true)
                    else details
                } as ArrayList<SourceEntity>)
        }

    }

    fun resetSelectedValue() {
        viewModelScope.launch {
            val categoryId = getPreferredCategory() ?: 0
            val countryId = getPreferredCountry() ?: 0
            val country =
                _uiStates.value.availableCountries.find { it.id == countryId }?.name
                    ?: "United States"

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)

            if (_uiStates.value.availableSources.isNotEmpty()) {
                val sources = getPreferredSources()
                if (sources!!.isNotEmpty()) {
                    val sourcesList = sources.split(",")

                    sourcesList.forEach {
                        toggleInitialSelectedSources(it)
                    }
                }
            }
        }
    }

    fun savePreferredSetting() {
        viewModelScope.launch {
            val categoryId = _uiStates.value.selectedCategory
            val countryId =
                _uiStates.value.availableCountries.find { it.name == _uiStates.value.selectedCountry }?.id
                    ?: 53
            val sources =
                _uiStates.value.availableSources.filter { it.selected }.map { it.id }
                    .joinToString(",")

            Log.e("testSavePreferredSetting", categoryId.toString())
            Log.e("testSavePreferredSetting", countryId.toString())
            Log.e("testSavePreferredSetting", sources.toString())
            dataStore.savePreferredCategoryId(categoryId)
            dataStore.savePreferredCountryId(countryId)
            dataStore.savePreferredSources(sources)

        }
    }

    suspend fun getPreferredCategory() = dataStore.preferredCategoryId.first()

    suspend fun getPreferredCountry() = dataStore.preferredCountryId.first()
    suspend fun getPreferredSources() = dataStore.preferredSources.first()


    //=======================================db function=========================================//

    suspend fun getAllSources() {
        val dbSources = repository.getAllSourcesFromDB()
        _uiStates.update { it.copy(availableSources = dbSources) }
        val categoryId = getPreferredCategory() ?: 0
        val countryId = getPreferredCountry() ?: 0
        val country =
            _uiStates.value.availableCountries.find { it.id == countryId }?.name ?: "United States"
        if (_uiStates.value.availableSources.isNotEmpty()) {
            val sources = getPreferredSources()

            toggleSelectedCategory(categoryId)
            toggleSelectedCountry(country)
            if (sources!!.isNotEmpty()) {
                val sourcesList = sources.split(",")

                sourcesList.forEach {
                    toggleInitialSelectedSources(it)
                }
            }
        }
    }

    suspend fun insertBookMark(articleEntity: ArticleEntity) {
        val rowId = repository.insertBookMarksDB(articleEntity)
        if (rowId > 0) {
            _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }
            context.showToast("Saved to Bookmarks")
        }
    }

    suspend fun getBookMarkArticles() {
        val bookMarkArticles = repository.getBookMarkArticleDB()
        _uiStates.update { it.copy(bookMarkArticles = bookMarkArticles.toCollection(ArrayList())) }
    }

    suspend fun removeBookMark(articleEntity: ArticleEntity) {
        repository.removeBookMarksDB(articleEntity.id)
        _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }
        context.showToast("Removed from Bookmarks")
    }

    suspend fun updateIsFav(articleEntity: ArticleEntity) {
        repository.updateIsFavDB(articleEntity.isFav, articleEntity.id)
        _uiStates.update { it.copy(recompose = !_uiStates.value.recompose) }

    }

    suspend fun getAllSourcesForArticle() {
        val dbSources = repository.getAllSourcesFromDB()
        _uiStates.update { it.copy(availableSources = dbSources) }


        if (_uiStates.value.availableSources.isNotEmpty()) {
            val sources = getPreferredSources()
            if (sources!!.isNotEmpty()) {
                val sourcesList = sources.split(",")
                sourcesList.forEach {
                    toggleInitialSelectedSources(it)
                }
            }
        }
    }

}
