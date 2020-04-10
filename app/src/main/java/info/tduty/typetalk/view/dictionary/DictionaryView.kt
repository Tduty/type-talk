package info.tduty.typetalk.view.dictionary

import info.tduty.typetalk.data.model.DictionaryVO

interface DictionaryView {

    fun showEmptyPlaceholder()

    fun hideEmptyPlaceholder()

    fun setDictionary(dictionaryList: List<DictionaryVO>)

}
