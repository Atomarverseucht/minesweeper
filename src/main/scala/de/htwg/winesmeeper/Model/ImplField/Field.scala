package de.htwg.winesmeeper.Model.ImplField

import de.htwg.winesmeeper.Model.FieldTrait
import jakarta.inject.Inject
import com.google.inject.assistedinject.Assisted

case class Field @Inject() (@Assisted("bomb") isBomb: Boolean,
                            @Assisted("opened") isOpened: Boolean,
                            @Assisted("flag") isFlag: Boolean = false) 
  extends FieldTrait