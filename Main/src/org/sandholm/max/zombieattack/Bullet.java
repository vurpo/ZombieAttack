package org.sandholm.max.zombieattack;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by max on 1/20/14.
 */
public class Bullet {
    private Vector2 position = new Vector2();
    private Vector2 velocity = new Vector2();

    public Bullet(Vector2 position, float direction, float velocity) {
        this.position = position;
        this.velocity.x = velocity;
        this.velocity.setAngle(direction);
    }

    public void update(float delta) {
        velocity.y -= 8*delta;
        position.add(velocity.cpy().scl(delta));
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public Vector2 getVelocity() {
        return this.velocity;
    }
}

/*var Bullet = function(x,y,direction,velocity) {
  this.prevX = x;
  this.prevY = y;
  this.x = x;
  this.y = y;
  this.gravity = 0;
  this.direction = direction;
  this.drawDirection = direction;
  this.velocity = velocity;
  this.update = function() {
    this.prevX = this.x;
    this.prevY = this.y;
    this.gravity += 0.25*manageTime.frameLength;
    this.x += Math.cos(this.direction+Math.PI)*this.velocity*manageTime.frameLength;
    this.y += Math.sin(this.direction+Math.PI)*this.velocity*manageTime.frameLength;
    this.y += this.gravity;
    this.drawDirection = Math.atan2(this.prevY-this.y, this.prevX-this.x);
    if (this.x < 0 || this.x > canvas.width || this.y > groundLevel) {
      return 1;
    }
    else {
      return 0;
    }
  }
  this.draw = function(context) {
    context.save();
    context.translate(this.x, this.y);
    context.rotate(this.drawDirection+Math.PI/2);
    context.beginPath();
    context.moveTo(0,0);
    context.lineTo(0,-5);
    context.stroke();
    context.restore();
  }
}*/