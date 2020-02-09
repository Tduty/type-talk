package info.tduty.typetalk.view.dictionary

import info.tduty.typetalk.data.model.DictionaryVO

// TODO: added action
interface DictionaryView {

    fun setDictionary(dictionaryList: List<DictionaryVO>)
}