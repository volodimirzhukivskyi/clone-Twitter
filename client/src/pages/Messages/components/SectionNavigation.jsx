import React from "react";
import {styled} from "@mui/material/styles";
import Navigation from './Navigation';

const SectionNavigation = () => {
  return (
    <SectionWrapper>
      <Navigation/>
    </SectionWrapper>);
}

const styles = ({theme}) => ({
  height: '100%',
  width: '100%',
  borderLeft: '1px solid #DDDFE2',
  borderRight: '1px solid #DDDFE2',

  [theme.breakpoints.up('md')]: {
    width: 'auto',
  },
});

const SectionWrapper = styled('section')(styles);

export default SectionNavigation;