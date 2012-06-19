using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Renderer
{
    public partial class WorldViewer : Form
    {
        const int CENTER = 250;
        const int SQUARE_SIZE = 16;
        double mag = 1.0;

        Ant.World world = new Ant.World();

        public WorldViewer()
        {
            InitializeComponent();

            var t = new Timer();
            t.Interval = 20;
            t.Tick += UpdateWorld;
            t.Start();
        }

        private void UpdateWorld(object sender, EventArgs e)
        {
            world = Ant.Engine.Step(world);
            this.Refresh();
        }

        protected override void OnPaint(PaintEventArgs e)
        {
            base.OnPaint(e);

            world.Path.Select(ConvertToWorld).ToList().ForEach((c) => RenderSquare(c, BlockSize(), Color.Black));
            RenderSquare(ConvertToWorld(world.Ant), BlockSize(), Color.Red);
        }

        private int BlockSize()
        {
            return Convert.ToInt32(Math.Max(2.0, mag * SQUARE_SIZE));
        }

        private Ant.Coord ConvertToWorld(Ant.Coord c)
        {
            return new Ant.Coord(
                c.x * BlockSize() + CENTER,
                c.y * BlockSize() + CENTER
            );
        }

        private void RenderSquare( Ant.Coord c, int width, System.Drawing.Color color)
        {
            var myBrush = new System.Drawing.SolidBrush(color);
            System.Drawing.Graphics formGraphics;
            formGraphics = this.CreateGraphics();
            formGraphics.FillRectangle(myBrush, new Rectangle(c.x, c.y, width, width));

            myBrush.Dispose();
            formGraphics.Dispose();
        }

        protected override void OnKeyPress(KeyPressEventArgs e)
        {
            base.OnKeyPress(e);

            switch (e.KeyChar)
            {
                case ('+'):
                    mag *= 0.5;
                    this.Refresh();
                    break;
                case ('-'):
                    mag *= 2.0;
                    this.Refresh();
                    break;
                case (' '):
                    mag = 1.0;
                    this.Refresh();
                    break;
                case ('Q'):
                case ('q'):
                    Application.Exit();
                    break;
            }
        }
    }
}
