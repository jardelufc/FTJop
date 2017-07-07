/*
 * Copyright (c) 2007,2008, Stefan Hepp
 *
 * This file is part of JOPtimizer.
 *
 * JOPtimizer is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * JOPtimizer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jopdesign.libgraph.cfg.statements.quad;

import com.jopdesign.libgraph.cfg.statements.AssignStmt;
import com.jopdesign.libgraph.cfg.statements.VariableStmt;
import com.jopdesign.libgraph.cfg.statements.common.BinopStmt;
import com.jopdesign.libgraph.cfg.statements.stack.StackBinop;
import com.jopdesign.libgraph.cfg.statements.stack.StackStatement;
import com.jopdesign.libgraph.cfg.variable.Variable;
import com.jopdesign.libgraph.cfg.variable.VariableTable;
import com.jopdesign.libgraph.struct.ConstantValue;
import com.jopdesign.libgraph.struct.type.TypeInfo;

/**
 * @author Stefan Hepp, e0026640@student.tuwien.ac.at
 */
public class QuadBinop extends BinopStmt implements QuadStatement, AssignStmt, VariableStmt {

    private Variable result;
    private Variable value1;
    private Variable value2;

    public QuadBinop(TypeInfo type, int operand, Variable result, Variable value1, Variable value2) {
        super(type, operand);
        this.result = result;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getCodeLine() {
        return result.getName() + " = " + value1.getName() + " " + getOperator() + " " + value2.getName();
    }

    public StackStatement[] getStackCode(VariableTable varTable) {
        return new StackStatement[] {
                QuadHelper.createLoad(this, 0),
                QuadHelper.createLoad(this, 0),
                new StackBinop(getType(), getOperand()),
                QuadHelper.createStore(this)
        };
    }

    public boolean isConstant() {
        return false;
    }

    public ConstantValue evaluateConstantStmt() {
        return null;
    }

    public Variable getAssignedVar() {
        return result;
    }

    public Variable[] getUsedVars() {
        return new Variable[] { value1, value2 };
    }

    public TypeInfo getAssignedType() {
        int op = getOperand();
        if ( op == OP_CMP || op == OP_CMPG || op == OP_CMPL ) {
            return TypeInfo.CONST_INT ;
        }
        return getType();
    }

    public void setAssignedVar(Variable newVar) {
        result = newVar;
    }

    public TypeInfo[] getUsedTypes() {
        int op = getOperand();
        if ( op == OP_SHIFT_LEFT || op == OP_LOGIC_SHIFT_RIGHT || op == OP_SHIFT_RIGHT ) {
            return new TypeInfo[] { getType(), TypeInfo.CONST_INT };
        }
        return new TypeInfo[] { getType(), getType() };
    }

    public void setUsedVar(int i, Variable var) {
        if ( i == 0 ) {
            value1 = var;
        }
        if ( i == 1 ) {
            value2 = var;
        }
    }
}
