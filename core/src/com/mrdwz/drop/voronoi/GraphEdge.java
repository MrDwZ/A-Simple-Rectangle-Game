/*
  Copyright 2011 James Humphreys. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are
permitted provided that the following conditions are met:

   1. Redistributions of source code must retain the above copyright notice, this list of
      conditions and the following disclaimer.

   2. Redistributions in binary form must reproduce the above copyright notice, this list
      of conditions and the following disclaimer in the documentation and/or other materials
      provided with the distribution.

THIS SOFTWARE IS PROVIDED BY James Humphreys ``AS IS'' AND ANY EXPRESS OR IMPLIED
WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

The views and conclusions contained in the software and documentation are those of the
authors and should not be interpreted as representing official policies, either expressed
or implied, of James Humphreys.
 */

package com.mrdwz.drop.voronoi;

public class GraphEdge
{
    public double x1, y1, x2, y2;

    public int site1;
    public int site2;

    public GraphEdge(){}
    public GraphEdge(double x1, double y1, double x2, double y2) {
        this.x1 = x1; this.y1 = y1;
        this.x2 = x2; this.y2 = y2;
    }
    @Override
    public boolean equals(Object o) {
        GraphEdge p = (GraphEdge)o;
        if  (this.x1 == p.x1 && this.x2 == p.x2 && this.y1 == p.y1 && this.y2 == p.y2) return true;
        if  (this.x1 == p.x2 && this.x2 == p.x1 && this.y1 == p.y2 && this.y2 == p.y1) return true;
        return false;
    }

    public double length() {
        return Math.sqrt(((this.x1 - this.x2) * (this.x1 - this.x2)) +
                ((this.y1 - this.y2) * (this.y1 - this.y2)));
    }
}
