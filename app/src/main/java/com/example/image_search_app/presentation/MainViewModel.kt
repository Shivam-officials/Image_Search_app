package com.example.image_search_app.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.image_search_app.domain.model.Image
import com.example.image_search_app.domain.use_cases.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GetImagesUseCase,
) : ViewModel() {

    private val _query = MutableStateFlow("");
    fun updateQuery(q: String) {
        _query.update { q }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val images = _query
        .filter { it.isNotBlank()  } // Ignore empty or blank queries
        .debounce { 1000 } // Wait for 1 second before processing to avoid rapid API calls
        .flatMapLatest { _query ->
            // If a new query comes in, cancel the previous request and start a new one
            useCase.invoke(_query).flow
        }
        .cachedIn(viewModelScope) // Cache the flow results in ViewModel scope to prevent recomputation on UI re-subscribe on configuration changes

}