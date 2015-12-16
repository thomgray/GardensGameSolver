/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gardens;

/**
 *
 * @author thomdikdave
 */
public enum State {
    Tree, Unknown, Empty, Void;
    
    public static State shiftState(State in){
        switch (in) {
            case Unknown: return Empty;
            case Empty: return Tree;
            case Tree: return Unknown;
            case Void: return Void;
            default:
                throw new AssertionError();
        }
    }
}


