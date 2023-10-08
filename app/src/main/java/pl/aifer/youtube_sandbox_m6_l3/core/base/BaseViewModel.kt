package pl.aifer.youtube_sandbox_m6_l3.core.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {
    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun <T> doOperation(
        operation: suspend () -> Result<T>,
        success: (T) -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            val result = operation()
            when {
                result.isSuccess -> result.onSuccess(success)
                result.isFailure -> result.onFailure { throwable ->
                    if (!throwable.message.isNullOrEmpty())
                        _error.postValue(throwable.message)
                }
            }
            _loading.postValue(false)
        }
    }
}