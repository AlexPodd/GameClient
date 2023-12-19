package com.mygdx.game.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Skeleton extends Enemy{
    private Projectile projectile;

    public Skeleton(float x, float y, float HP, float Damage, float AttackSpeed, float MoveSpeed) {
        super(x, y, HP, Damage, AttackSpeed, MoveSpeed);
        texture = new Texture("Skeleton/SpriteSheet.png");
        EnemyAnim = new TextureRegion[texture.getWidth()][texture.getHeight()];
        pos.set(x,y);
        CreateAnim();
        projectile = new Projectile();
    }

    @Override
    protected void CreateAnim() {
        super.CreateAnim();
    }

    @Override
    protected void Animation(Batch batch) {
        super.Animation(batch);
    }
    @Override
    protected void RandomMoving() {
        super.RandomMoving();
    }

    @Override
    public void Die() {
        super.Die();
    }

    @Override
    public void render(Batch batch, Vector2 PosProj) {
        projectile = new Projectile(PosProj);

        projectile.RenderProj(batch);
        super.render(batch);
    }
    class Projectile{
        private Texture projectileTexture;
        private Vector2 PosProj;
        public Projectile(Vector2 PosProj){
            this.PosProj = PosProj;
            projectileTexture = new Texture("Skeleton/зуб.png");
        }
        public Projectile(){}

        public void RenderProj(Batch batch){
            batch.draw(projectileTexture, PosProj.x,PosProj.y);
        }

    }
}
