/*
 * Copyright (C) 2015 Pedro Vicente Gomez Sanchez.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.github.pedrovgs.androidgameboyemulator.core.processor.isa;

import com.github.pedrovgs.androidgameboyemulator.core.processor.GBZ80;
import com.github.pedrovgs.androidgameboyemulator.core.processor.Register;

class Inc8BitRegister extends Instruction {

  private final Register sourceRegister;

  Inc8BitRegister(GBZ80 z80, Register sourceRegister) {
    super(z80);
    this.sourceRegister = sourceRegister;
  }

  @Override public void execute() {
    byte registerValue = z80.get8BitRegisterValue(sourceRegister);
    byte result = (byte) (registerValue + 1);
    z80.set8BitRegisterValue(sourceRegister, result);
    z80.setLastInstructionExecutionTime(1);

    boolean wasCyEnabled = z80.isFlagCYEnabled();
    z80.resetFlagF();
    if (wasCyEnabled) {
      z80.enableFlagCY();
    }
    if (result == 0) {
      z80.enableFlagZ();
    }
    if ((result & 0xF) == 0xF) {
      z80.enableFlagH();
    }
  }
}
