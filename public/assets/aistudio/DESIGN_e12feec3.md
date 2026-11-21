---
name: Tender Steps Health System
colors:
  surface: '#f7f9ff'
  surface-dim: '#d7dadf'
  surface-bright: '#f7f9ff'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f1f4f9'
  surface-container: '#ebeef3'
  surface-container-high: '#e5e8ee'
  surface-container-highest: '#e0e3e8'
  on-surface: '#181c20'
  on-surface-variant: '#3f4946'
  inverse-surface: '#2d3135'
  inverse-on-surface: '#eef1f6'
  outline: '#6f7977'
  outline-variant: '#bfc9c5'
  surface-tint: '#23695f'
  primary: '#00443d'
  on-primary: '#ffffff'
  primary-container: '#135d54'
  on-primary-container: '#91d3c8'
  inverse-primary: '#90d3c7'
  secondary: '#a83900'
  on-secondary: '#ffffff'
  secondary-container: '#fc6018'
  on-secondary-container: '#531800'
  tertiary: '#7f000b'
  on-tertiary: '#ffffff'
  tertiary-container: '#a80c16'
  on-tertiary-container: '#ffb4ad'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#acefe3'
  primary-fixed-dim: '#90d3c7'
  on-primary-fixed: '#00201c'
  on-primary-fixed-variant: '#005048'
  secondary-fixed: '#ffdbcf'
  secondary-fixed-dim: '#ffb59a'
  on-secondary-fixed: '#380d00'
  on-secondary-fixed-variant: '#802a00'
  tertiary-fixed: '#ffdad6'
  tertiary-fixed-dim: '#ffb4ac'
  on-tertiary-fixed: '#410003'
  on-tertiary-fixed-variant: '#93000e'
  background: '#f7f9ff'
  on-background: '#181c20'
  surface-variant: '#e0e3e8'
typography:
  headline-xl:
    fontFamily: Plus Jakarta Sans
    fontSize: 36px
    fontWeight: '700'
    lineHeight: 44px
  headline-xl-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 34px
  headline-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 36px
  headline-lg-mobile:
    fontFamily: Plus Jakarta Sans
    fontSize: 24px
    fontWeight: '700'
    lineHeight: 30px
  headline-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 22px
    fontWeight: '600'
    lineHeight: 28px
  headline-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 18px
    fontWeight: '600'
    lineHeight: 24px
  body-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 16px
    fontWeight: '500'
    lineHeight: 24px
  body-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 15px
    fontWeight: '400'
    lineHeight: 22px
  body-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 13px
    fontWeight: '400'
    lineHeight: 18px
  label-lg:
    fontFamily: Plus Jakarta Sans
    fontSize: 15px
    fontWeight: '600'
    lineHeight: 20px
  label-md:
    fontFamily: Plus Jakarta Sans
    fontSize: 13px
    fontWeight: '600'
    lineHeight: 16px
  label-sm:
    fontFamily: Plus Jakarta Sans
    fontSize: 11px
    fontWeight: '700'
    lineHeight: 14px
rounded:
  sm: 0.25rem
  DEFAULT: 0.5rem
  md: 0.75rem
  lg: 1rem
  xl: 1.5rem
  full: 9999px
spacing:
  gutter: 1rem
  gutter-sm: 0.75rem
  gutter-lg: 1.5rem
  margin: 1rem
  margin-sm: 0.75rem
  margin-lg: 1.5rem
  space-xs: 0.25rem
  space-sm: 0.5rem
  space-md: 1rem
  space-lg: 1.5rem
  space-xl: 2rem
---

## Brand & Style

This design system is crafted for maternal, neonatal, and pediatric healthcare tracking across Togo and West Africa. The tone is deeply reassuring, empathetic, protective, and culturally resonant. It prioritizes clarity, non-intimidating visual language, and intuitive accessibility for caregivers, community health workers, and mothers under varying degrees of digital literacy.

The aesthetic blends **Modern Warmth** with **Humanist Tactile Ergonomics**:
- Clean, uncluttered layouts with expansive whitespace to alleviate medical anxiety.
- Organic softness expressed through generous border radii, gentle warm tints, and soft elevations.
- High-contrast visual affordances designed to withstand harsh outdoor sunlight on mid-range Android screens.
- Prominent vocal accessibility triggers and oversized touch targets (minimum 48–56px) for rapid, single-handed operation while holding a child.

## Colors

The palette balances institutional clinical trust with maternal warmth and unmistakable emergency triage clarity:

- **Primary (`#135d54`) — Deep Emerald Health**: Anchor color conveying medical authority, growth, life, and serene confidence. Used for primary navigation, core CTAs, progress indicators, and confirmed states.
- **Secondary (`#e65100`) — Warm Terracotta / Solar Orange**: Culturally grounding and energetic tone reflecting vitality, care, and attention. Applied to secondary actions, active badges, immunization alerts, and audio/voice triggers.
- **Tertiary (`#c62828`) — Critical Alert Red**: Reserved exclusively for clinical triage, dehydration/fever warnings, emergency calling, and critical danger signs. Meets strict WCAG AAA contrast against light backgrounds.
- **Neutral Surface & Background (`#f9f9ff` to `#ffffff`)**: Cool, soft tinted canvas preventing eye strain, coupled with stark pure white `#ffffff` elevated cards for distinct visual hierarchy.
- **Text & Neutral Contrast (`#212529`)**: High-legibility deep slate for body copy ensuring WCAG AA/AAA compliance across all display types.

## Typography

**Plus Jakarta Sans** is utilized universally across display, body, and label roles. Its geometric underpinnings softened by friendly, open apertures ensure optimum legibility across lower-resolution mobile screens and varying ambient lighting conditions.

- **Headlines**: Weighted at `600` and `700` to deliver immediate, assertive hierarchy without appearing aggressive.
- **Body**: Base sizes remain at or above `15px` with a minimum `1.4` line height to maximize legibility for users with mild visual impairment or non-native literacy contexts.
- **Labels**: Generous x-height and bold tracking ensure tags, dosage metrics, and medical timeline milestones are decipherable at a glance.

## Layout & Spacing

The layout is built around a mobile-first fluid single-column structure that expands into a 4-column system on compact screens and 8-columns on tablets.

- **Outer Margins**: Fixed at `16px` (`margin`) on mobile screens to preserve maximum screen real estate, increasing to `24px` (`margin-lg`) on tablets.
- **Rhythm & Touch Ergonomics**: Minimum touch bounding-boxes are maintained at `48px × 48px` (ideally `56px` for primary calls to action). Elements within cards and forms rely on `space-md` (`16px`) spacing to prevent accidental taps.
- **Thumb Zones**: Primary action triggers and voice guidance FABs are anchored in the lower 35% of the screen.

## Elevation & Depth

Visual hierarchy uses soft tonal layering combined with subtle, warm-tinted ambient shadows to maintain a tactile, physical quality without harshness:

- **Level 0 (Canvas)**: Background surface in `#f9f9ff`. Flat, distraction-free.
- **Level 1 (Cards & Modules)**: Pure white `#ffffff` surfaces raised by a soft ambient shadow: `box-shadow: 0 4px 16px rgba(19, 93, 84, 0.06), 0 1px 3px rgba(0, 0, 0, 0.04)`. Outlines are kept at `1px solid rgba(19, 93, 84, 0.08)`.
- **Level 2 (Active Sheets & Navigation)**: Floating headers, bottom navigation bars, and modal sheets: `box-shadow: 0 8px 24px rgba(19, 93, 84, 0.12)`.
- **Level 3 (Emergency & Floating Voice Prompts)**: Critical emergency action badges and voice guidance overlays utilize higher elevation: `box-shadow: 0 12px 32px rgba(198, 40, 40, 0.22)`.

## Shapes

The design system adopts a **Rounded (`2`)** shape posture, with components defaulting to `8px` (`0.5rem`), cards and content sheets scaling to `16px` (`rounded-lg`), and interactive tags or emergency buttons taking smooth pill and container geometries (`rounded-xl` at `24px` to fully rounded capsules). 

This soft curvature echoes the maternal, protective ethos of the brand, neutralizing clinical intimidation while maintaining systematic structure.

## Components

### Buttons
- **Primary Action (Emerald)**: `#135d54` background with pure white text, minimum height `52px`, `rounded-lg` (12px), bold font (`label-lg`), with subtle green tinted glow.
- **Secondary Action (Warm Terracotta)**: `#e65100` background or filled container with white text; used for non-clinical next steps like appointment booking and dietary guidance.
- **Emergency Button**: Minimum height `56px`, `#c62828` bold fill with high-contrast emergency white iconography (stethoscope, telephone) and tactile feedback states.
- **Vocal Accessibility Button**: Distinctive floating capsule with microphone/speaker glyph, `#f57c00` background, high-contrast white audio wave icon, minimum target `56px × 56px`, fixed at the bottom right thumb reach.

### Cards
- **Child Health Passport & Milestones**: White `#ffffff` background, `rounded-lg` (16px), bordered with `1px solid rgba(19, 93, 84, 0.08)`, inner padding `space-md` (16px).
- **Vaccination / Alert Cards**: Surface tinted with light variant (`#fff3e0` for reminders, `#ffebee` for urgent checkups), complemented by a 4px solid left indicator border.

### Form Inputs
- Minimum touch field height `52px`, `rounded-md` (8px), background `#ffffff`, border `1.5px solid #d0d7de`, active/focus state `2px solid #135d54` with `rgba(19, 93, 84, 0.15)` focus ring.
- Accompanying integrated audio assist button adjacent to labels to read input instructions aloud.

### Chips & Badges
- Pill-shaped (`rounded-full`), height `32px`, padding `0 12px`.
- Growth status: Normal (`#135d54` text on `#e0f2f1` container), Attention (`#e65100` text on `#ffe0b2` container), Alert (`#c62828` text on `#ffcdd2` container).

### Checkboxes & Radios
- Enlarged touch perimeter (`24px × 24px` glyph embedded within a minimum `48px` clickable container).
- Check mark tinted `#135d54` with high-contrast check graphic.