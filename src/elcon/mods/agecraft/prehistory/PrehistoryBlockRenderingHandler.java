package elcon.mods.agecraft.prehistory;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.prehistory.blocks.BlockRock;
import elcon.mods.agecraft.prehistory.blocks.BlockRock.RockShape;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

public class PrehistoryBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return 200;
	}
	
	@Override
	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		switch(modelID) {
		case 200:
			return renderBlockRock(blockAccess, x, y, z, block, modelID, renderer);
		case 201:
			return renderBlockCampfire(blockAccess, x, y, z, block, modelID, renderer);
		}
		return false;
	}
	
	private boolean renderBlockRock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		RockShape shape = BlockRock.shapes[blockAccess.getBlockMetadata(x, y, z)];
		renderer.setRenderBounds(shape.minX, shape.minY, shape.minZ, shape.maxX, shape.maxY, shape.maxZ);
		renderer.renderStandardBlock(block, x, y, z);
		return true;
	}
	
	private boolean renderBlockCampfire(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		TileEntityCampfire tile = (TileEntityCampfire) blockAccess.getBlockTileEntity(x, y, z);
		if(tile.frameStage > 0) {
			if(tile.frameDirection == 0) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[0]));
					renderer.setRenderBounds(0.0D, 0.0D, 0.4D, 0.1D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[1]));
					renderer.setRenderBounds(0.9D, 0.0D, 0.4D, 1.0D, 1.0D, 0.6D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[2]));
					renderer.setRenderBounds(0.1D, 0.9D, 0.45D, 0.9D, 1.0D, 0.55D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			} else if(tile.frameDirection == 1) {
				if(tile.frameStage >= 1) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[0]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.0D, 0.6D, 1.0D, 0.1D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 2) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[1]));
					renderer.setRenderBounds(0.4D, 0.0D, 0.9D, 0.6D, 1.0D, 1.0D);
					renderer.renderStandardBlock(block, x, y, z);
				}
				if(tile.frameStage >= 3) {
					renderer.setOverrideBlockTexture(Trees.planks.getIcon(0, tile.frameType[2]));
					renderer.setRenderBounds(0.45D, 0.9D, 0.1D, 0.55D, 1.0D, 0.9D);
					renderer.renderStandardBlock(block, x, y, z);
				}
			}
			renderer.clearOverrideBlockTexture();
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}	
}
